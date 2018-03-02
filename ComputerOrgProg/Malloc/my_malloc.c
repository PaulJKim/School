#include "my_malloc.h"

/* You *MUST* use this macro when calling my_sbrk to allocate the
 * appropriate size. Failure to do so may result in an incorrect
 * grading!
 */
#define SBRK_SIZE 2048

/* Please use this value as your canary! */
#define CANARY 0x2110CAFE

/* If you want to use debugging printouts, it is HIGHLY recommended
 * to use this macro or something similar. If you produce output from
 * your code then you may receive a 20 point deduction. You have been
 * warned.
 */
#ifdef DEBUG
#define DEBUG_PRINT(x) printf x
#else
#define DEBUG_PRINT(x)
#endif


/* our freelist structure - this is where the current freelist of
 * blocks will be maintained. failure to maintain the list inside
 * of this structure will result in no credit, as the grader will
 * expect it to be maintained here.
 * Technically this should be declared static for the same reasons
 * as above, but DO NOT CHANGE the way this structure is declared
 * or it will break the autograder.
 */
metadata_t* freelist;

#define to_metadata(i) ( (metadata_t*)(((char*)i)-(sizeof(metadata_t) + sizeof(int))) )

void* my_malloc(size_t size)
{
  /* FIX ME */
	int user_request_size = size + sizeof(metadata_t) + 2*sizeof(int);
	if(user_request_size > SBRK_SIZE) {
		ERRNO = SINGLE_REQUEST_TOO_LARGE;
		return NULL;
	}
	if(freelist == NULL) {
		freelist = (metadata_t*)my_sbrk(SBRK_SIZE);
		if(freelist == NULL) {
			ERRNO = OUT_OF_MEMORY;
			return NULL;
		}
		freelist->block_size = 2048;
		freelist->request_size = 0;
		freelist->prev = NULL;
		freelist->next = NULL;
	}

	metadata_t* cur_ptr = freelist;

	//find either block large enough or end of freelist
	while(cur_ptr->next != NULL && user_request_size > cur_ptr->block_size) {
		cur_ptr = cur_ptr->next;
	}
	
	if(cur_ptr->next == NULL && cur_ptr->block_size < user_request_size) {
		metadata_t* new_mem = (metadata_t*)my_sbrk(SBRK_SIZE);
		if(new_mem == NULL) {
			ERRNO = OUT_OF_MEMORY;
			return NULL;
		}
		new_mem->block_size = 2048;
		new_mem->request_size = 0;
		new_mem->prev = cur_ptr;
		new_mem->next = NULL;
		//Set cur next to newly allocated mem then move cur pointer to it
		cur_ptr->next = new_mem;
		cur_ptr = cur_ptr->next;
	}


	//makes new free block to put into freelist
	metadata_t* new_meta = (metadata_t*)((char*)cur_ptr + user_request_size);
	new_meta->block_size = cur_ptr->block_size - user_request_size;
	new_meta->request_size = 0;

	if(cur_ptr->prev != NULL) 
		cur_ptr->prev->next = new_meta;
	else
		freelist=new_meta;
	new_meta->prev = cur_ptr->prev;
	new_meta->next = cur_ptr->next;
	if(cur_ptr->next != NULL) {
		cur_ptr->next->prev = new_meta;
	}

	if((cur_ptr->block_size - user_request_size) < (sizeof(metadata_t) + 2*sizeof(int) + 1)) {
		cur_ptr->block_size = cur_ptr->block_size + (cur_ptr->block_size - user_request_size);
	}
	cur_ptr->block_size = user_request_size;
	cur_ptr->request_size = size;
	cur_ptr->next = NULL;
	cur_ptr->prev = NULL;

	//Make and set canaries
	int* front_canary = (int*)((char*)cur_ptr + sizeof(metadata_t));
	int* back_canary = (int*)((char*)front_canary + sizeof(int) + size);
	*front_canary = CANARY;
	*back_canary = CANARY;

	ERRNO = NO_ERROR;

  	return ((char*)front_canary + sizeof(int));
}

void my_free(void* ptr)
{
	metadata_t* cur_ptr = to_metadata(ptr);
	int* front_canary = (int*)((char*)cur_ptr + sizeof(metadata_t));
	int* back_canary = (int*)((char*)front_canary + sizeof(int) + cur_ptr->request_size);
	//check if canaries have been corrupted
	if(*front_canary != CANARY) {
		ERRNO = CANARY_CORRUPTED;
		return;
	}
	if(*back_canary != CANARY) {
		ERRNO = CANARY_CORRUPTED;
		return;
	}

	metadata_t* freelist_ptr = freelist;
	//check if data to free belongs in front of freelist
	if(cur_ptr < freelist) {
		cur_ptr->next = freelist;
		freelist->prev = cur_ptr;
		freelist = cur_ptr;
		//if data to free is next to current freelist, merge
		if((metadata_t*)((char*)cur_ptr + cur_ptr->block_size) == freelist_ptr) {
			freelist->block_size = cur_ptr->block_size + cur_ptr->next->block_size;
			freelist->next = freelist->next->next;
			if(freelist->next != NULL) {
				freelist->next->prev = freelist;
			}
		}
	} else {
		while(freelist_ptr->next != NULL && cur_ptr > freelist_ptr->next) {
			freelist_ptr = freelist_ptr->next;
		}
		if(freelist_ptr->next == NULL) {
			freelist_ptr->next = cur_ptr;
			cur_ptr->prev = freelist_ptr;
			if((metadata_t*)((char*)freelist_ptr + freelist_ptr->block_size) == cur_ptr) {
				freelist_ptr->block_size = freelist_ptr->block_size + cur_ptr->block_size;
				cur_ptr->prev = NULL;
				freelist_ptr->next = NULL;
			}
		} else {
			cur_ptr->next = freelist_ptr->next;
			cur_ptr->prev = freelist_ptr;
			freelist_ptr->next->prev = cur_ptr;
			freelist_ptr->next = cur_ptr;
			if((metadata_t*)((char*)cur_ptr + cur_ptr->block_size) == cur_ptr->next) {
				cur_ptr->block_size = cur_ptr->block_size + cur_ptr->next->block_size;
				cur_ptr->next->prev = NULL;
				cur_ptr->next = cur_ptr->next->next;
			} else if((metadata_t*)((char*)freelist_ptr + freelist_ptr->block_size) == cur_ptr) {
				freelist_ptr->block_size = freelist_ptr->block_size + freelist_ptr->next->block_size;
				freelist_ptr->next->prev = NULL;
				freelist_ptr->next = freelist_ptr->next->next;
			}
		}
	}

  /* FIX ME */
}

