#include "mylib.h"
#include "mooncrater.h"
#include "lander.h"
#include "turret.h"
#include "bullet.h"
#include <stdlib.h>
#include <time.h>

enum GBAState state;

int main() {

	REG_DISPCNT = MODE_3 | BG2_EN;
	state = START;
	int col = 0;
	int row = 5;
	int gravityDelay = 0;
	int colLeftWaiter = 1;
	int colLeftDelay = 0;
	int colRightWaiter = 1;
	int colRightDelay = 0;
	int verticalV = 1;
	int leftV = 1;
	int rightV = 1;
	int timer = 0;
	int bulletLeftDelay = 0;
	int leftTurretCoords = 0;
	int bulletRightDelay = 0;
	int rightTurretCoords = 15;
	int bulletDelay = 0;
	struct bullet bullets[100];


	volatile unsigned short pressedKey = BUTTONS;

	//Game loop
	while(1) {		

		switch(state) {
			case START:
				fillScreen3(BLACK);
				drawString3(60, 4, "LUNAR LANDER",WHITE);
				drawString3(70, 4, "PRESS START",WHITE);

				struct turret turret1;
				turret1.row = 134;
				turret1.col = 15;
				struct turret turret2;
				turret2.row = 134;
				turret2.col = 220;
				timer++;
				srand(timer);
				col = rand() % 210 + 15;
				state = START_NODRAW;
				break;
			case START_NODRAW:
				if(KEY_DOWN_NOW(BUTTON_START) && pressedKey != BUTTONS) {
					
					for(int i = 0; i<15; i++) {
						bullets[i].row = 129;
						bullets[i].col = 20;
						bullets[i].xVel = rand()%11+5;
						bullets[i].yVel = rand()%8+4;
						bullets[i].exists = 0;
						bullets[i].lr = 3;
					}
					for(int i = 15; i<30; i++) {
						bullets[i].row = 129;
						bullets[i].col = 215;
						bullets[i].xVel = rand()%11+5;
						bullets[i].yVel = rand()%8+4;
						bullets[i].exists = 0;
						bullets[i].lr = 4;
					}
				state = GAMESCREEN1;
			}
				break;
			case GAMESCREEN1:
				drawBackground(mooncrater);
				drawImage3(turret1.row, turret1.col, TURRET_WIDTH, TURRET_HEIGHT, turret);
				drawImage3(turret2.row, turret2.col, TURRET_WIDTH, TURRET_HEIGHT, turret);
				state = GAMESCREEN1_NODRAW;
				break;
			case GAMESCREEN1_NODRAW:
				
				if(KEY_DOWN_NOW(BUTTON_SELECT)) {	
					state = START;
				}
				if(KEY_DOWN_NOW(BUTTON_LEFT && pressedKey == BUTTONS)) {
					colRightWaiter = 0;
					colLeftWaiter = 1;
					colLeftDelay += 5;
				} 
				if(KEY_DOWN_NOW(BUTTON_RIGHT && pressedKey == BUTTONS)) {
					colLeftWaiter = 0;
					colRightWaiter = 1;
					colRightDelay += 5;
				}

				if(KEY_DOWN_NOW(BUTTON_UP) && verticalV > 1) {
					gravityDelay += 1;
				}
				if(KEY_DOWN_NOW(BUTTON_DOWN)) {
					gravityDelay +=3;
				}
				if(!KEY_DOWN_NOW(BUTTON_UP)) {
					gravityDelay += 2;
				}

				if(bulletLeftDelay > 2500) {
					drawImage3(bullets[leftTurretCoords].row, bullets[leftTurretCoords].col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
					leftTurretCoords++;
					bullets[leftTurretCoords].exists = 1;
					bulletLeftDelay = 0;
				}
				if(bulletRightDelay > 3000) {
					drawImage3(bullets[rightTurretCoords].row, bullets[rightTurretCoords].col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
					rightTurretCoords++;
					bullets[rightTurretCoords].exists = 1;
					bulletRightDelay = 0;
				}

				if(gravityDelay > 500) {
					row = row + verticalV;
					gravityDelay = 0;
				}
				if(colLeftWaiter == 1 && colLeftDelay > 450 && col > 0) {
					col = col - leftV;
					colLeftDelay = 0;
				}
				if(colRightWaiter == 1 && colRightDelay > 450 && col < (240-LANDER_WIDTH)) {
					col = col + rightV;
					colRightDelay = 0;
				}
				// if(makeBulletDelay > 4000) {
				// 	newBullet.row = 129;
				// 	newBullet.col = 20;
				// 	drawImage3(newBullet.row, newBullet.col, BULLET_WIDTH, BULLET_HEIGHT, bullet);
				// } else if(shootBulletDelay > 400) {
				// 	newBullet.row = newBullet.row + verticalV;
				// 	newBullet.col = newBullet.col + rightV;
				// }
				gravityDelay += 2;
				colLeftDelay += 1;
				colRightDelay += 1;
				bulletLeftDelay += 1;
				bulletRightDelay += 1;
				bulletDelay += 1;
				//bulletDrawDelay += 1;

				if(row > (turret1.row - TURRET_HEIGHT) && col > (turret1.col - TURRET_WIDTH) && col < (turret1.col + 11)) {
					state = ENDSCREEN;
				}
				if(row > (turret2.row - TURRET_HEIGHT) && col > (turret2.col - 11) && col < (turret2.col + (TURRET_WIDTH))) {
					state = ENDSCREEN;
				}
				if(row > 128 && col <= 240 && col >= 0) {
					state = WINSCREEN;
				}
				

				// if(bulletDrawDelay > 3000) {
				// 	drawBullet(*bullets);
				// }
				// if(bulletDelay > 500) {
				// 	updateBullets(bullets);
				// }
				drawImage3(row, col, LANDER_WIDTH, LANDER_HEIGHT, lander);
				if(bulletDelay > 500) {
					updateBullets(bullets, row, col);
					bulletDelay = 0;
				}
				waitForVBlank();
				break;
			case WINSCREEN:
				drawBackground(mooncrater);
				drawString3(60, 70, "YOU WIN!",WHITE);
				drawString3(70, 70, "PRESS ENTER TO PLAY AGAIN",WHITE);
				col = rand() % 230 + 10;
				row = 5;
				state = WINSCREEN_NODRAW;
				break;
			case WINSCREEN_NODRAW:
				if(KEY_DOWN_NOW(BUTTON_START) && pressedKey != BUTTONS) {
				state = START;
				pressedKey = BUTTONS;
			}
				break;
			case ENDSCREEN:
				fillScreen3(BLACK);
				drawString3(60, 4, "GAME OVER", WHITE);
				drawString3(70, 4, "PRESS START TO PLAY AGAIN", WHITE);
				col = rand() % 230 + 10;
				row = 5;
				state = ENDSCREEN_NODRAW;
				break;
			case ENDSCREEN_NODRAW:
				if(KEY_DOWN_NOW(BUTTON_START) && pressedKey != BUTTONS) {
					state = START;
					pressedKey = BUTTONS;
				}
				if(KEY_DOWN_NOW(BUTTON_SELECT)) {	
					state = START;
				}
				break;

		}

		pressedKey = BUTTONS;
		//waitForVBlank();

	}

	return 0;
}

void checkCollision() {

}