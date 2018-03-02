#include <stdlib.h>
#include <time.h>
#include "my_malloc.h"

#define to_metadata(i) ( (metadata_t*)(((char*)i)-(sizeof(metadata_t) + sizeof(int))) )

int main() {
    /* do some testing */
    
	return 0;
}
