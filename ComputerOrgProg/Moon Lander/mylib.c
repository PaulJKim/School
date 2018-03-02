#include "mylib.h"
#include "bullet.h"
#include <stdlib.h>
#include "lander.h"

unsigned short *videoBuffer = (unsigned short *)0x6000000;
enum GBAState state;



void fillScreen3(volatile u16 color) {
	DMA[3].src = &color;
	DMA[3].dst = videoBuffer;
	DMA[3].cnt = 38400 | DMA_ON | DMA_SOURCE_FIXED;
}

void setPixel(int r, int c, unsigned short color) {
	videoBuffer[OFFSET(r, c, 240)] = color;
}

void drawChar(int row, int col, char ch, u16 color) {
	for(int r=0; r<8; r++)
	{
		for(int c=0; c<6; c++)
		{
			if(fontdata_6x8[OFFSET(r, c, 6) + ch*48])
			{
				setPixel(row+r, col+c, color);
			}
		}
	}
}

void drawRect(int row, int col, int height, int width, u16 color) {
    for (int r = 0; r < height; r++) {
        DMA[3].src = &color;
        DMA[3].dst = &videoBuffer[OFFSET(row + r, col, 240)];
        DMA[3].cnt = width | DMA_ON | DMA_SOURCE_FIXED;
    }
}

void drawBackground(const u16 *image) {
	DMA[DMA_CHANNEL_3].src = image;
	DMA[DMA_CHANNEL_3].dst = videoBuffer + 240;
	DMA[DMA_CHANNEL_3].cnt = (240*160) | DMA_SOURCE_INCREMENT | DMA_DESTINATION_INCREMENT | DMA_ON;
}

void drawImage3(int row, int col, int width, int height, const u16 *image) {
	for (int i = 0; i < height; i++) {
		DMA[3].src = image + (i* width);
		DMA[3].dst = videoBuffer + OFFSET(row + i, col, 240);
		DMA[3].cnt = width | DMA_ON;
	}
}

void drawString3(int row, int col, const char *str, u16 color) {
	while(*str)
	{
		drawChar(row, col, *str++, color);
		col += 6;
	}
}

void updateBullets(struct bullet bullets[], int row, int col) {
	for(int i = 0; i < 30; i++) {
		if(bullets[i].exists && bullets[i].row <= 0) {
			bullets[i].exists = 0;
			bullets[i].row = 0;
			bullets[i].col = 0;
		} else if(bullets[i].exists && bullets[i].lr == 3){
			bullets[i].row -= bullets[i].yVel;
			bullets[i].col += bullets[i].xVel;
			bullets[i].exists = 1;
			drawRect(bullets[i].row+bullets[i].yVel, bullets[i].col-bullets[i].xVel, BULLET_WIDTH, BULLET_HEIGHT, BLACK);
			drawImage3(bullets[i].row, bullets[i].col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
		} else if(bullets[i].exists && bullets[i].lr == 4){
			bullets[i].row -= bullets[i].yVel;
			bullets[i].col -= bullets[i].xVel;
			bullets[i].exists = 1;
			drawRect(bullets[i].row+bullets[i].yVel, bullets[i].col+bullets[i].xVel, BULLET_WIDTH, BULLET_HEIGHT, BLACK);
			drawImage3(bullets[i].row, bullets[i].col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
		}
		if(bullets[i].row > row && bullets[i].row < row+17 && bullets[i].col > col-3 && bullets[i].col < col + 12) {
			state = ENDSCREEN;
		}
	}
}

void drawBullet(TBullet bullets[]) {
	for(unsigned int i = 0; i < (sizeof(*bullets)/sizeof(bullets[0])); i++) {
		if(bullets[i].exists) {
			drawImage3(bullets[0].row, bullets[0].col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
		}
	}
}

// void moveLeft() {

// }

void waitForVBlank() {
    while (SCANLINECOUNTER < 160);
}