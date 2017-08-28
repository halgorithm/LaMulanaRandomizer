package lmr.randomizer.rcd;

import lmr.randomizer.rcd.object.GameObject;
import lmr.randomizer.rcd.object.Screen;

/**
 * Created by thezerothcat on 8/21/2017.
 */
public class PotMover {
    private static boolean templeOfMoonlightRemovedPotPresent;
    private static boolean towerOfTheGoddessRemovedPotPresent;

    private static Screen templeOfMoonlightRemovedPotScreen;
    private static Screen towerOfTheGoddessRemovedPotScreen;

    public static void init() {
        templeOfMoonlightRemovedPotPresent = false;
        towerOfTheGoddessRemovedPotPresent = false;
    }

    public static void addRemovedPots() {
        if(!towerOfTheGoddessRemovedPotPresent) {
            GameObject towerOfTheGoddessRemovedPot = new GameObject(towerOfTheGoddessRemovedPotScreen);
            towerOfTheGoddessRemovedPot.setId((short)0x00);
            towerOfTheGoddessRemovedPot.setX(1500);
            towerOfTheGoddessRemovedPot.setY(400);

            towerOfTheGoddessRemovedPot.getArgs().add((short)0);
            towerOfTheGoddessRemovedPot.getArgs().add((short)0);
            towerOfTheGoddessRemovedPot.getArgs().add((short)-1);
            towerOfTheGoddessRemovedPot.getArgs().add((short)1);
            towerOfTheGoddessRemovedPot.getArgs().add((short)13);
            towerOfTheGoddessRemovedPot.getArgs().add((short)105);
            towerOfTheGoddessRemovedPot.getArgs().add((short)35);
            towerOfTheGoddessRemovedPot.getArgs().add((short)17);
            towerOfTheGoddessRemovedPot.getArgs().add((short)0);

            towerOfTheGoddessRemovedPotScreen.getObjects().add(towerOfTheGoddessRemovedPot);
        }
        if(!templeOfMoonlightRemovedPotPresent) {
            GameObject templeOfMoonlightRemovedPot = new GameObject(templeOfMoonlightRemovedPotScreen);
            templeOfMoonlightRemovedPot.setId((short)0x00);
            templeOfMoonlightRemovedPot.setX(540);
            templeOfMoonlightRemovedPot.setY(240);

            templeOfMoonlightRemovedPot.getArgs().add((short)0);
            templeOfMoonlightRemovedPot.getArgs().add((short)0);
            templeOfMoonlightRemovedPot.getArgs().add((short)-1);
            templeOfMoonlightRemovedPot.getArgs().add((short)1);
            templeOfMoonlightRemovedPot.getArgs().add((short)12);
            templeOfMoonlightRemovedPot.getArgs().add((short)105);
            templeOfMoonlightRemovedPot.getArgs().add((short)35);
            templeOfMoonlightRemovedPot.getArgs().add((short)17);
            templeOfMoonlightRemovedPot.getArgs().add((short)0);

            templeOfMoonlightRemovedPotScreen.getObjects().add(templeOfMoonlightRemovedPot);
        }
    }

    public static void updateLocation(GameObject obj) {
        Screen containingScreen = (Screen)obj.getObjectContainer(); // All pots are linked to a Screen
        if(containingScreen.getZoneIndex() == 0) {
            // Gate of Guidance
            if(containingScreen.getRoomIndex() == 4 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 180) {
                    obj.setX(160);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 2) {
            // Mausoleum of the Giants
            if(containingScreen.getRoomIndex() == 1 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 220) {
                    obj.setX(20);
                    obj.setY(320);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 7) {
            // Twin Labyrinths
            if(containingScreen.getRoomIndex() == 0 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 400) {
                    obj.setX(100);
                }
            }
            else if(containingScreen.getRoomIndex() == 2 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 680) {
                    obj.setX(660);
                }
                else if(obj.getX() == 720) {
                    obj.setX(700);
                }
            }
            else if(containingScreen.getRoomIndex() == 3 && containingScreen.getScreenIndex() == 2) {
                if(obj.getX() == 100) {
                    obj.setX(20);
                }
            }
            else if(containingScreen.getRoomIndex() == 8 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 1080) {
                    obj.setX(1040);
                }
            }
            else if(containingScreen.getRoomIndex() == 9 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 1140) {
                    obj.setX(840);
                }
            }
            else if(containingScreen.getRoomIndex() == 11 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 680) {
                    obj.setX(660);
                }
            }
            else if(containingScreen.getRoomIndex() == 12 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 160) {
                    obj.setX(20);
                }
            }
            else if(containingScreen.getRoomIndex() == 12 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 580 && obj.getY() == 800) {
                    obj.setY(640);
                }
                else if(obj.getX() == 20 && obj.getY() == 640) {
                    obj.setY(800);
                }
                else if(obj.getX() == 260) {
                    obj.setX(200);
                }
            }
            else if(containingScreen.getRoomIndex() == 16 && containingScreen.getScreenIndex() == 2) {
                if(obj.getX() == 40) {
                    obj.setX(20);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 8) {
            // Endless Corridor
            if(containingScreen.getRoomIndex() == 5 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 180) {
                    obj.setX(160);
                }
                else if(obj.getX() == 2120) {
                    obj.setX(2100);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 9) {
            // Shrine of the Mother
            if(containingScreen.getRoomIndex() == 0 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 120) {
                    obj.setX(100);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 10) {
            // Gate of Illusion
            if(containingScreen.getRoomIndex() == 7 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 200) {
                    obj.setX(20);
                    obj.setY(320);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 11) {
            // Graveyard of the Giants
            if(containingScreen.getRoomIndex() == 3 && containingScreen.getScreenIndex() == 0) {
                if(obj.getY() == 400) {
                    obj.setY(320);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 12) {
            // Temple of Moonlight
            if(containingScreen.getRoomIndex() == 0 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 120) {
                    obj.setX(20);
                    obj.setY(400);
                }
            }
            else if(containingScreen.getRoomIndex() == 1 && containingScreen.getScreenIndex() == 0) {
                templeOfMoonlightRemovedPotScreen = containingScreen;
                if(obj.getX() == 540) {
                    templeOfMoonlightRemovedPotPresent = true;
                }
            }
            else if(containingScreen.getRoomIndex() == 3 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 380) {
                    obj.setX(300);
                    obj.setY(160);
                }
            }
            else if(containingScreen.getRoomIndex() == 4 && containingScreen.getScreenIndex() == 2) {
                if(obj.getX() == 100) {
                    obj.setX(80);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 13) {
            // Tower of the Goddess
            if(containingScreen.getRoomIndex() == 1 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 500) {
                    obj.setX(460);
                }
            }
            else if(containingScreen.getRoomIndex() == 1 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 120) {
                    obj.setX(100);
                }
            }
            else if(containingScreen.getRoomIndex() == 3 && containingScreen.getScreenIndex() == 1) {
                if(obj.getX() == 40) {
                    obj.setX(20);
                }
            }
            else if(containingScreen.getRoomIndex() == 4 && containingScreen.getScreenIndex() == 0) {
                if(obj.getX() == 40) {
                    obj.setX(20);
                }
            }
            else if(containingScreen.getRoomIndex() == 6 && containingScreen.getScreenIndex() == 2) {
                if(obj.getX() == 40) {
                    obj.setX(20);
                }
            }
            else if(containingScreen.getRoomIndex() == 6 && containingScreen.getScreenIndex() == 3) {
                if(obj.getX() == 260) {
//                    obj.getArgs().set(4, (short)0);
                }
            }
            else if(containingScreen.getRoomIndex() == 7 && containingScreen.getScreenIndex() == 2) {
                towerOfTheGoddessRemovedPotScreen = containingScreen;
                if(obj.getX() == 1460) {
                    obj.setX(1440);
                }
                else if(obj.getX() == 1500) {
                    towerOfTheGoddessRemovedPotPresent = true;
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 15) {
            // Chamber of Birth (East)
            if (containingScreen.getRoomIndex() == 0 && containingScreen.getScreenIndex() == 1) {
                if (obj.getX() == 120) {
                    obj.setX(20);
                    obj.setY(880);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 16) {
            // Chamber of Birth (West)
            if (containingScreen.getRoomIndex() == 0 && containingScreen.getScreenIndex() == 1) {
                if (obj.getX() == 780) {
                    obj.setX(760);
                }
            }
            else if (containingScreen.getRoomIndex() == 4 && containingScreen.getScreenIndex() == 0) {
                if (obj.getX() == 440) {
                    obj.setX(400);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 17) {
            // Dimensional Corridor
            if (containingScreen.getRoomIndex() == 2 && containingScreen.getScreenIndex() == 1) {
                if (obj.getX() == 720) {
                    obj.setX(700);
                }
            }
            else if (containingScreen.getRoomIndex() == 4 && containingScreen.getScreenIndex() == 0) {
                if (obj.getX() == 380) {
                    obj.setX(400);
                }
            }
            else if (containingScreen.getRoomIndex() == 8 && containingScreen.getScreenIndex() == 0) {
                if (obj.getX() == 60) {
                    obj.setX(40);
                }
            }
            else if (containingScreen.getRoomIndex() == 10 && containingScreen.getScreenIndex() == 1) {
                if (obj.getX() == 60) {
                    obj.setX(40);
                }
            }
        }
        else if(containingScreen.getZoneIndex() == 23) {
            if (containingScreen.getRoomIndex() == 10 && containingScreen.getScreenIndex() == 0) {
                if (obj.getX() == 140) {
                    obj.setX(120);
                }
                else if (obj.getX() == 40) {
                    obj.setX(480);
                }
            }
            else if (containingScreen.getRoomIndex() == 18 && containingScreen.getScreenIndex() == 1) {
                if (obj.getX() == 880) {
                    obj.setX(760);
                }
            }
        }
    }
}
