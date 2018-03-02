#include <stdlib.h>
typedef unsigned short u16;
extern const unsigned char fontdata_6x8[12288];

#define REG_DISPCNT *(unsigned short*) 0x4000000
#define MODE_3 3
#define BG2_EN (1 << 10)
#define SCANLINECOUNTER *(volatile u16 *)0x4000006

//colors
#define COLOR(R,G,B) (((R) & 0x1F) | (((G) & 0x1F) << 5) | (((B) & 0x1F) << 10))
#define BLACK   COLOR(0 , 0 , 0 )
#define WHITE   COLOR(31, 31, 31)
#define RED     COLOR(31, 0 , 0 )
#define GREEN   COLOR(0 , 31, 0 )
#define BLUE    COLOR(0 , 0 , 31)
#define YELLOW  COLOR(31, 31, 0 )
#define MAGENTA COLOR(31, 0 , 31)
#define CYAN    COLOR(0 , 31, 31)
#define ORANGE  COLOR(31, 15, 0 )
#define BROWN   COLOR(18, 9 , 0 )
#define PURPLE  COLOR(15, 0 , 15)
#define TEAL    COLOR(0 , 15, 15)
#define MAROON  COLOR(15, 0 , 0 )
#define GREY    COLOR(15, 15, 15)
#define PINK    COLOR(31, 18, 19)

//buttons
#define BUTTON_A		(1<<0)
#define BUTTON_B		(1<<1)
#define BUTTON_SELECT	(1<<2)
#define BUTTON_START	(1<<3)
#define BUTTON_RIGHT	(1<<4)
#define BUTTON_LEFT		(1<<5)
#define BUTTON_UP		(1<<6)
#define BUTTON_DOWN		(1<<7)
#define BUTTON_R		(1<<8)
#define BUTTON_L		(1<<9)

#define OFFSET(r, c, rowlen) ((r)*(rowlen) + (c))
#define KEY_DOWN_NOW(key)  (~(BUTTONS) & key)
#define KEY_PRESS(key, prev_key) ((~(BUTTONS) & key) && (key & prev_key))

#define BUTTONS *(volatile unsigned int *)0x4000130

/* DMA */

#define REG_DMA0SAD         *(const volatile u32*)0x40000B0 // source address
#define REG_DMA0DAD         *(volatile u32*)0x40000B4       // destination address
#define REG_DMA0CNT         *(volatile u32*)0x40000B8       // control register

// DMA channel 1 register definitions
#define REG_DMA1SAD         *(const volatile u32*)0x40000BC // source address
#define REG_DMA1DAD         *(volatile u32*)0x40000C0       // destination address
#define REG_DMA1CNT         *(volatile u32*)0x40000C4       // control register

// DMA channel 2 register definitions
#define REG_DMA2SAD         *(const volatile u32*)0x40000C8 // source address
#define REG_DMA2DAD         *(volatile u32*)0x40000CC       // destination address
#define REG_DMA2CNT         *(volatile u32*)0x40000D0       // control register

// DMA channel 3 register definitions
#define REG_DMA3SAD         *(const volatile u32*)0x40000D4 // source address
#define REG_DMA3DAD         *(volatile u32*)0x40000D8       // destination address
#define REG_DMA3CNT         *(volatile u32*)0x40000DC       // control register

typedef struct
{
	const volatile void *src;
	const volatile void *dst;
	unsigned int cnt;
} DMA_CONTROLLER;

struct turret {
	volatile int col;
	int row;
};

struct bullet {
	int row;
	int col;
	int xVel;
	int yVel;
	int exists;
	int lr;
};

typedef struct tBullet {
	int col;
	int row;
	int hspeed;
	int vspeed;
	int exists;
} TBullet;

#define DMA ((volatile DMA_CONTROLLER *) 0x040000B0)

// Defines
#define DMA_CHANNEL_0 0
#define DMA_CHANNEL_1 1
#define DMA_CHANNEL_2 2
#define DMA_CHANNEL_3 3

#define DMA_DESTINATION_INCREMENT (0 << 21)
#define DMA_DESTINATION_DECREMENT (1 << 21)
#define DMA_DESTINATION_FIXED (2 << 21)
#define DMA_DESTINATION_RESET (3 << 21)

#define DMA_SOURCE_INCREMENT (0 << 23)
#define DMA_SOURCE_DECREMENT (1 << 23)
#define DMA_SOURCE_FIXED (2 << 23)

#define DMA_REPEAT (1 << 25)

#define DMA_16 (0 << 26)
#define DMA_32 (1 << 26)

#define DMA_NOW (0 << 28)
#define DMA_AT_VBLANK (1 << 28)
#define DMA_AT_HBLANK (2 << 28)
#define DMA_AT_REFRESH (3 << 28)

#define DMA_IRQ (1 << 30)
#define DMA_ON (1 << 31)


enum GBAState {
	START,
	START_NODRAW,
	GAMESCREEN1,
	GAMESCREEN1_NODRAW,
	ENDSCREEN,
	ENDSCREEN_NODRAW,
	WINSCREEN,
	WINSCREEN_NODRAW
};

extern enum GBAState state;

//prototypes
void setPixel(int r, int c, unsigned short color);
void fillScreen3(unsigned short color);
void waitForVBlank();
void drawString3(int x, int y, const char *str, u16 color);
void drawChar(int row, int col, char ch, u16 color);
void drawBackground(const u16 *image);
// void moveLeft(int col);
void drawImage3(int row, int col, int width, int height, const u16 *image);
void updateBullets(struct bullet bullets[], int row, int col);
void drawBullet(TBullet bullets[]);
