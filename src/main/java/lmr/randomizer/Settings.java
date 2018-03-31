package lmr.randomizer;

import lmr.randomizer.random.BossDifficulty;
import lmr.randomizer.random.ShopRandomizationEnum;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by thezerothcat on 7/20/2017.
 */
public final class Settings {
    public static final List<String> POSSIBLE_REMOVED_ITEMS = Arrays.asList("Spaulder");
    public static final int MAX_RANDOM_REMOVED_ITEMS_CURRENTLY_SUPPORTED = 40;

    public static Set<String> currentRemovedItems;
    public static String currentStartingWeapon;

    private static Settings singleton = new Settings();

    private long startingSeed;

    private boolean changed = false;

    private boolean automaticHardmode;
    private boolean coinChestGraphics;
    private boolean requireSoftwareComboForKeyFairy;
    private boolean requireIceCapeForLava;
    private boolean requireFlaresForExtinction;
    private boolean randomizeForbiddenTreasure;
    private boolean randomizeCoinChests;
    private boolean randomizeTrapItems;
    private boolean randomizeMainWeapon;
    private boolean replaceMapsWithWeights;
    private boolean automaticGrailPoints;

    private boolean quickStartItemsEnabled;

    private List<String> enabledGlitches = new ArrayList<>();
    private List<String> enabledDamageBoosts = new ArrayList<>();

    private List<String> possibleGlitches = Arrays.asList("Lamp Glitch", "Cat Pause",
            "Raindrop", "Ice Raindrop", "Pot Clip", "Object Zip");
    private List<String> possibleDboosts = Arrays.asList("Item", "Environment", "Enemy");

    private List<String> possibleRandomizedItems = Arrays.asList("Holy Grail", "Hand Scanner", "reader.exe",
            "Hermes' Boots", "Grapple Claw", "Feather", "Isis' Pendant", "Bronze Mirror", "mirai.exe", "bunemon.exe",
            "Random", "xmailer.exe");

    private String laMulanaBaseDir;
    private String laMulanaSaveDir;
    private String language;

    private Set<String> nonRandomizedItems = new HashSet<>();
    private Set<String> initiallyAccessibleItems = new HashSet<>();
    private Set<String> surfaceItems = new HashSet<>();
    private Set<String> removedItems = new HashSet<>();

    private int minRandomRemovedItems;
    private int maxRandomRemovedItems;

    private String xmailerItem;

    private BossDifficulty bossDifficulty;
    private ShopRandomizationEnum shopRandomization;

    private Settings() {
        startingSeed = new Random().nextInt(Integer.MAX_VALUE);
        laMulanaBaseDir = "Please enter your La-Mulana install directory";
        language = "en";

        requireSoftwareComboForKeyFairy = true;
        requireIceCapeForLava = true;
        requireFlaresForExtinction = true;
        randomizeForbiddenTreasure = false;
        randomizeCoinChests = true;
        randomizeTrapItems = true;
        randomizeMainWeapon = false;
        replaceMapsWithWeights = false;
        automaticHardmode = false;
        coinChestGraphics = false;
        automaticGrailPoints = false;
        quickStartItemsEnabled = false;

        bossDifficulty = BossDifficulty.MEDIUM;
        shopRandomization = ShopRandomizationEnum.EVERYTHING;

        xmailerItem = null;

        minRandomRemovedItems = 0;
        maxRandomRemovedItems = 0;

        for (String filename : Arrays.asList("C:\\Games\\La-Mulana Remake 1.3.3.1", "C:\\GOG Games\\La-Mulana", "C:\\GOG Games\\La-Mulana",
                "C:\\Steam\\steamapps\\common\\La-Mulana", "C:\\Program Files (x86)\\Steam\\steamapps\\common\\La-Mulana",
                "C:\\Program Files\\Steam\\steamapps\\common\\La-Mulana", "C:\\Program Files (x86)\\GOG Galaxy\\Games\\La Mulana",
                "C:\\Program Files (x86)\\GOG.com\\La-Mulana"
                /* Steam on Linux path? */)) {
            if (new File(filename).exists()) {
                laMulanaBaseDir = filename;
                break;
            }
        }
        
        try {
            // Try to find the GOG game on Linux
            // Also honor file system hierachy (local installs supersede global installs)
            for (String menu_entry_file_path : Arrays.asList(
                    "/usr/share/applications/gog_com-La_Mulana_1.desktop",
                    "/usr/local/share/applications/gog_com-La_Mulana_1.desktop",
                    System.getProperty("user.home") + "/.local/share/applications/gog_com-La_Mulana_1.desktop",
                    System.getProperty("user.home") + "/Desktop/gog_com-La_Mulana_1.desktop"
                    /* other valid paths for the .desktop file to be located? */)) {
                
                File menu_entry_file = new File(menu_entry_file_path);
                if (!menu_entry_file.exists()) {
                    continue; // Try next item if file doesn't exist
                }
                
                List<String> menu_file_lines = Files.readAllLines(menu_entry_file.toPath());
                menu_file_lines.removeIf(l -> !l.startsWith("Path="));
                
                if (menu_file_lines.size() != 1) {
                    continue; // File is malformed, there should be exactly one "Path=..." line
                }
                
                laMulanaBaseDir = menu_file_lines.get(0).substring(5);
            }
            
            // The GOG version has some fluff around the *actual* game install, moving it into the
            // "game" subdirectory. If it exists, then just use that, otherwise the rcdReader won't
            // be able to find the necessary files!
            File dir = new File(laMulanaBaseDir, "game");
            if (dir.exists() && dir.isDirectory()) {
                laMulanaBaseDir += "/game";
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public static boolean isChanged() {
        return singleton.changed;
    }

    public static long getStartingSeed() {
        return singleton.startingSeed;
    }

    public static void setStartingSeed(int startingSeed) {
        singleton.startingSeed = startingSeed;
    }

    public static String getLaMulanaBaseDir() {
        return singleton.laMulanaBaseDir;
    }

    public static void setLaMulanaBaseDir(String laMulanaBaseDir, boolean update) {
        if(update && !laMulanaBaseDir.equals(singleton.laMulanaBaseDir)) {
            singleton.changed = true;
        }
        singleton.laMulanaBaseDir = laMulanaBaseDir;
    }

    public static String getLaMulanaSaveDir() {
        return singleton.laMulanaSaveDir;
    }

    public static void setLaMulanaSaveDir(String laMulanaSaveDir, boolean update) {
        if(update && !laMulanaSaveDir.equals(singleton.laMulanaSaveDir)) {
            singleton.changed = true;
        }
        singleton.laMulanaSaveDir = laMulanaSaveDir;
    }

    public static String getLanguage() {
        return singleton.language;
    }

    public static void setLanguage(String language, boolean update) {
        if(update && !language.equals(singleton.language)) {
            singleton.changed = true;
        }
        singleton.language = language;
    }

    public static String getBackupDatFile() {
        if("en".equals(singleton.language)) {
            return "script_code.dat.bak";
        }
        return "script_code_" + singleton.language + ".dat.bak";
    }

    public static String getXmailerItem() {
        return singleton.xmailerItem;
    }

    public static void setXmailerItem(String xmailerItem, boolean update) {
        if(update) {
            if(xmailerItem == null && singleton.xmailerItem != null
                    || xmailerItem != null && xmailerItem.equals(singleton.xmailerItem)) {
                singleton.changed = true;
            }
        }
        singleton.xmailerItem = xmailerItem;
    }

    public static ShopRandomizationEnum getShopRandomization() {
        return singleton.shopRandomization;
    }

    public static void setShopRandomization(String shopRandomization, boolean update) {
        if(update && !shopRandomization.equals(singleton.shopRandomization.toString())) {
            singleton.changed = true;
        }
        singleton.shopRandomization = ShopRandomizationEnum.valueOf(shopRandomization);
    }

    public static BossDifficulty getBossDifficulty() {
        return singleton.bossDifficulty;
    }

    public static void setBossDifficulty(String bossDifficulty, boolean update) {
        if(update && !bossDifficulty.equals(singleton.bossDifficulty.toString())) {
            singleton.changed = true;
        }
        singleton.bossDifficulty = BossDifficulty.valueOf(bossDifficulty);
    }

    public static boolean isRequireSoftwareComboForKeyFairy() {
        return singleton.requireSoftwareComboForKeyFairy;
    }

    public static void setRequireSoftwareComboForKeyFairy(boolean requireSoftwareComboForKeyFairy, boolean update) {
        if(update && requireSoftwareComboForKeyFairy != singleton.requireSoftwareComboForKeyFairy) {
            singleton.changed = true;
        }
        singleton.requireSoftwareComboForKeyFairy = requireSoftwareComboForKeyFairy;
    }

    public static boolean isRequireIceCapeForLava() {
        return singleton.requireIceCapeForLava;
    }

    public static void setRequireIceCapeForLava(boolean requireIceCapeForLava, boolean update) {
        if(update && requireIceCapeForLava != singleton.requireIceCapeForLava) {
            singleton.changed = true;
        }
        singleton.requireIceCapeForLava = requireIceCapeForLava;
    }

    public static boolean isRequireFlaresForExtinction() {
        return singleton.requireFlaresForExtinction;
    }

    public static void setRequireFlaresForExtinction(boolean requireFlaresForExtinction, boolean update) {
        if(update && requireFlaresForExtinction != singleton.requireFlaresForExtinction) {
            singleton.changed = true;
        }
        singleton.requireFlaresForExtinction = requireFlaresForExtinction;
    }

    public static boolean isRandomizeMainWeapon() {
        return singleton.randomizeMainWeapon;
    }

    public static void setRandomizeMainWeapon(boolean randomizeMainWeapon, boolean update) {
        if(update && randomizeMainWeapon != singleton.randomizeMainWeapon) {
            singleton.changed = true;
        }
        singleton.randomizeMainWeapon = randomizeMainWeapon;
    }

    public static boolean isRandomizeForbiddenTreasure() {
        return singleton.randomizeForbiddenTreasure;
    }

    public static void setRandomizeForbiddenTreasure(boolean randomizeForbiddenTreasure, boolean update) {
        if(update && randomizeForbiddenTreasure != singleton.randomizeForbiddenTreasure) {
            singleton.changed = true;
        }
        singleton.randomizeForbiddenTreasure = randomizeForbiddenTreasure;
    }

    public static boolean isRandomizeCoinChests() {
        return singleton.randomizeCoinChests;
    }

    public static void setRandomizeCoinChests(boolean randomizeCoinChests, boolean update) {
        if(update && randomizeCoinChests != singleton.randomizeCoinChests) {
            singleton.changed = true;
        }
        singleton.randomizeCoinChests = randomizeCoinChests;
    }

    public static boolean isRandomizeTrapItems() {
        return singleton.randomizeTrapItems;
    }

    public static void setRandomizeTrapItems(boolean randomizeTrapItems, boolean update) {
        if(update && randomizeTrapItems != singleton.randomizeTrapItems) {
            singleton.changed = true;
        }
        singleton.randomizeTrapItems = randomizeTrapItems;
    }

    public static boolean isReplaceMapsWithWeights() { return singleton.replaceMapsWithWeights; }

    public static void setReplaceMapsWithWeights(boolean replaceMapsWithWeights, boolean update) {
        if(update && replaceMapsWithWeights != singleton.replaceMapsWithWeights) {
            singleton.changed = true;
        }
        singleton.replaceMapsWithWeights = replaceMapsWithWeights;
    }

    public static boolean isAutomaticHardmode() {
        return singleton.automaticHardmode;
    }

    public static void setAutomaticHardmode(boolean automaticHardmode, boolean update) {
        if(update && automaticHardmode != singleton.automaticHardmode) {
            singleton.changed = true;
        }
        singleton.automaticHardmode = automaticHardmode;
    }

    public static boolean isAutomaticGrailPoints() {
        return singleton.automaticGrailPoints;
    }

    public static void setAutomaticGrailPoints(boolean automaticGrailPoints, boolean update) {
        if(update && automaticGrailPoints != singleton.automaticGrailPoints) {
            singleton.changed = true;
        }
        singleton.automaticGrailPoints = automaticGrailPoints;
    }

    public static boolean isCoinChestGraphics() {
        return singleton.coinChestGraphics;
    }

    public static void setCoinChestGraphics(boolean coinChestGraphics, boolean update) {
        if(update && coinChestGraphics != singleton.coinChestGraphics) {
            singleton.changed = true;
        }
        singleton.coinChestGraphics = coinChestGraphics;
    }

    public static boolean isQuickStartItemsEnabled() {
        return singleton.quickStartItemsEnabled;
    }

    public static void setQuickStartItemsEnabled(boolean quickStartItemsEnabled, boolean update) {
        if(update && quickStartItemsEnabled != singleton.quickStartItemsEnabled) {
            singleton.changed = true;
        }
        singleton.quickStartItemsEnabled = quickStartItemsEnabled;
    }

    public static Set<String> getNonRandomizedItems() {
        return singleton.nonRandomizedItems;
    }

    public static void setNonRandomizedItems(Set<String> nonRandomizedItems, boolean update) {
        if(update && !singleton.changed) {
            if(nonRandomizedItems.containsAll(singleton.nonRandomizedItems)) {
                singleton.changed = !singleton.nonRandomizedItems.containsAll(nonRandomizedItems);
            }
            else {
                singleton.changed = true;
            }
        }

        singleton.nonRandomizedItems = nonRandomizedItems;
    }

    public static Set<String> getSurfaceItems() {
        return new HashSet<>(0);
    }

    public static void setSurfaceItems(Set<String> surfaceItems, boolean update) {
        if(update && !singleton.changed) {
            if(surfaceItems.containsAll(singleton.surfaceItems)) {
                singleton.changed = !singleton.surfaceItems.containsAll(surfaceItems);
            }
            else {
                singleton.changed = true;
            }
        }
        singleton.surfaceItems = surfaceItems;
    }

    public static Set<String> getInitiallyAccessibleItems() {
        return singleton.initiallyAccessibleItems;
    }

    public static void setInitiallyAccessibleItems(Set<String> initiallyAccessibleItems, boolean update) {
        if(update && !singleton.changed) {
            if(initiallyAccessibleItems.containsAll(singleton.initiallyAccessibleItems)) {
                singleton.changed = !singleton.initiallyAccessibleItems.containsAll(initiallyAccessibleItems);
            }
            else {
                singleton.changed = true;
            }
        }
        singleton.initiallyAccessibleItems = initiallyAccessibleItems;
    }

    public static List<String> getEnabledGlitches() {
        return singleton.enabledGlitches;
    }

    public static void setEnabledGlitches(List<String> enabledGlitches, boolean update) {
        if(update && !singleton.changed) {
            if (enabledGlitches.containsAll(singleton.enabledGlitches)) {
                singleton.changed = !singleton.enabledGlitches.containsAll(enabledGlitches);
            } else {
                singleton.changed = true;
            }
        }
        singleton.enabledGlitches = enabledGlitches;
    }

    public static List<String> getEnabledDamageBoosts() {
        return singleton.enabledDamageBoosts;
    }

    public static void setEnabledDamageBoosts(List<String> enabledDamageBoosts, boolean update) {
        if(update && !singleton.changed) {
            if (enabledDamageBoosts.containsAll(singleton.enabledDamageBoosts)) {
                singleton.changed = !singleton.enabledDamageBoosts.containsAll(enabledDamageBoosts);
            } else {
                singleton.changed = true;
            }
        }
        singleton.enabledDamageBoosts = enabledDamageBoosts;
    }

    public static int getMinRandomRemovedItems() {
        return singleton.minRandomRemovedItems;
    }

    public static void setMinRandomRemovedItems(int minRandomRemovedItems, boolean update) {
        if(minRandomRemovedItems > MAX_RANDOM_REMOVED_ITEMS_CURRENTLY_SUPPORTED || minRandomRemovedItems < 0) {
            return;
        }
        if(update && minRandomRemovedItems != singleton.minRandomRemovedItems) {
            singleton.changed = true;
        }
        singleton.minRandomRemovedItems = minRandomRemovedItems;
    }

    public static int getMaxRandomRemovedItems() {
        return singleton.maxRandomRemovedItems;
    }

    public static void setMaxRandomRemovedItems(int maxRandomRemovedItems, boolean update) {
        if(maxRandomRemovedItems > MAX_RANDOM_REMOVED_ITEMS_CURRENTLY_SUPPORTED || maxRandomRemovedItems < 0) {
            return;
        }
        if(update && maxRandomRemovedItems != singleton.maxRandomRemovedItems) {
            singleton.changed = true;
        }
        singleton.maxRandomRemovedItems = maxRandomRemovedItems;
    }

    public static Set<String> getRemovedItems() {
        return new HashSet<>(Arrays.asList("mantra.exe", "xmailer.exe", "guild.exe", "beolamu.exe", "emusic.exe", "Scriptures"));
//        return singleton.removedItems;
    }

    public static void setRemovedItems(Set<String> removedItems, boolean update) {
        if(update && !singleton.changed) {
            if(removedItems.containsAll(singleton.removedItems)) {
                singleton.changed = !singleton.removedItems.containsAll(removedItems);
            }
            else {
                singleton.changed = true;
            }
        }
        singleton.removedItems = removedItems;
    }

    public static Set<String> getCurrentRemovedItems() {
        return currentRemovedItems;
    }

    public static void setCurrentRemovedItems(Set<String> currentRemovedItems) {
        Settings.currentRemovedItems = currentRemovedItems;
    }

    public static String getCurrentStartingWeapon() {
        return singleton.currentStartingWeapon == null ? "Whip" : singleton.currentStartingWeapon;
    }

    public static void setCurrentStartingWeapon(String currentStartingItem) {
        singleton.currentStartingWeapon = currentStartingItem;
    }

    public static List<String> getStartingItems() {
        if(singleton.quickStartItemsEnabled) {
            return Arrays.asList("Holy Grail", "Hermes' Boots", "mirai.exe", "Spaulder");
        }
        return Arrays.asList("Spaulder");
    }

    public static void saveSettings() {
        if(singleton.changed) {
            try {
                FileUtils.saveSettings();
            } catch (IOException ex) {
                FileUtils.log("Unable to save settings: " + ex.getMessage());
            }
        }
    }

    public static int itemSetToInt(Collection<String> selectedItems, List<String> possibleItems) {
        int value = 0;
        for (String s : selectedItems) {
            int index = possibleItems.indexOf(s);
            if (index >= 0) {
                value |= 1 << index;
            }
        }
        return value;
    }

    public static Collection<String> intToItemSet(int input, List<String> possibleItems) {
        Collection<String> items = new ArrayList<>();

        int index = 0;

        while(input > 0) {
            if((input & 1) == 1) {
                items.add(possibleItems.get(index));
            }

            index++;
            input >>= 1;
        }

        return items;
    }

    public static int boolToInt(boolean b) {
        return b?1:0;
    }

    public static boolean intToBool(int i) {
        return i>0;
    }

    public static String generateShortString() {
        String result = FileUtils.VERSION;

        String separator = "-";

        //seed

        //boolean fields + shoprandomization enum
        BiFunction<Boolean, Integer, Integer> processBooleanFlag = (Boolean b, Integer flagIndex) -> boolToInt(b) << flagIndex;

        int booleanSettings = 0;
        booleanSettings |= processBooleanFlag.apply(singleton.automaticHardmode, 9);
        booleanSettings |= processBooleanFlag.apply(singleton.coinChestGraphics, 8);
        booleanSettings |= processBooleanFlag.apply(singleton.requireSoftwareComboForKeyFairy, 7);
        booleanSettings |= processBooleanFlag.apply(singleton.requireIceCapeForLava, 6);
        booleanSettings |= processBooleanFlag.apply(singleton.requireFlaresForExtinction, 5);
        booleanSettings |= processBooleanFlag.apply(singleton.randomizeForbiddenTreasure, 4);
        booleanSettings |= processBooleanFlag.apply(singleton.randomizeCoinChests, 3);
        booleanSettings |= processBooleanFlag.apply(singleton.randomizeTrapItems, 2);
        booleanSettings |= processBooleanFlag.apply(singleton.replaceMapsWithWeights, 1);
        booleanSettings |= processBooleanFlag.apply(singleton.automaticGrailPoints, 0);
        booleanSettings = booleanSettings << 2 | singleton.shopRandomization.ordinal();

        //glitches
        int glitches = itemSetToInt(getEnabledGlitches(), singleton.possibleGlitches);

        //dboosts
        int dboosts = itemSetToInt(getEnabledDamageBoosts(), singleton.possibleDboosts);

        //nonrandomized items
        int nonRandoItems = itemSetToInt(getNonRandomizedItems(), singleton.possibleRandomizedItems);

        //initially accessible items
        int initItems = itemSetToInt(getInitiallyAccessibleItems(), singleton.possibleRandomizedItems);

        //surface items
        int surfaceItems = itemSetToInt(getSurfaceItems(), singleton.possibleRandomizedItems);

        //removed items
        int removedItems = itemSetToInt(getRemovedItems(), POSSIBLE_REMOVED_ITEMS);

        // xmailer item
        int xmailer = singleton.possibleRandomizedItems.indexOf(singleton.xmailerItem);

        if(singleton.xmailerItem == null || xmailer == -1) {
            xmailer = singleton.possibleRandomizedItems.indexOf("Random");
        }

        // boss difficulty
        int bossDifficulty = singleton.bossDifficulty.ordinal();

        // combine the results of the settings in a string
        long startingSeed = getStartingSeed();
        result += separator + Long.toHexString(startingSeed);
        result += separator + Integer.toHexString(booleanSettings);
        result += separator + Integer.toHexString(glitches);
        result += separator + Integer.toHexString(dboosts);
        result += separator + Integer.toHexString(nonRandoItems);
        result += separator + Integer.toHexString(initItems);
        result += separator + Integer.toHexString(surfaceItems);
        result += separator + Integer.toHexString(removedItems);
        result += separator + Integer.toHexString(xmailer);
        result += separator + Integer.toHexString(bossDifficulty);
        result += separator + Integer.toHexString(singleton.minRandomRemovedItems);
        result += separator + Integer.toHexString(singleton.maxRandomRemovedItems);

        return result;
    }

    public static void importShortString(String text) {
        String[] parts = text.split("-");

        // Check version compatibility?
        if(!FileUtils.VERSION.equals(parts[0])) {
            // Show pop up that the version changed
            int version_mismatch = JOptionPane.showConfirmDialog(null, "These settings were generated with a different version of the randomizer. Do you  still want to try loading them?", "Version Mismatch", JOptionPane.YES_NO_OPTION);

            if(version_mismatch != JOptionPane.OK_OPTION) {
                return;
            }
        }

        // Parse seed from string
        int seed = Integer.parseInt(parts[1], 16);

        // Parse boolean settings from string
        int booleanSettingsFlag = Integer.parseInt(parts[2], 16);

        singleton.shopRandomization = ShopRandomizationEnum.values()[booleanSettingsFlag & 0x3];
        booleanSettingsFlag >>= 2;

        BiFunction<Integer, Integer, Boolean> getBoolFlagFromInt = (startingVal, flagIdx) -> intToBool((startingVal >> flagIdx) & 0x1);

        singleton.automaticHardmode = getBoolFlagFromInt.apply(booleanSettingsFlag, 9);
        singleton.coinChestGraphics = getBoolFlagFromInt.apply(booleanSettingsFlag, 8);
        singleton.requireSoftwareComboForKeyFairy = getBoolFlagFromInt.apply(booleanSettingsFlag, 7);
        singleton.requireIceCapeForLava = getBoolFlagFromInt.apply(booleanSettingsFlag, 6);
        singleton.requireFlaresForExtinction = getBoolFlagFromInt.apply(booleanSettingsFlag, 5);
        singleton.randomizeForbiddenTreasure = getBoolFlagFromInt.apply(booleanSettingsFlag, 4);
        singleton.randomizeCoinChests = getBoolFlagFromInt.apply(booleanSettingsFlag, 3);
        singleton.randomizeTrapItems = getBoolFlagFromInt.apply(booleanSettingsFlag, 2);
        singleton.replaceMapsWithWeights = getBoolFlagFromInt.apply(booleanSettingsFlag, 1);
        singleton.automaticGrailPoints = getBoolFlagFromInt.apply(booleanSettingsFlag, 0);

        Collection<String> glitches = intToItemSet(Integer.parseInt(parts[3],16), singleton.possibleGlitches);
        Collection<String> dboosts = intToItemSet(Integer.parseInt(parts[4],16), singleton.possibleDboosts);
        Set<String> nonRandoItems = new HashSet<>(intToItemSet(Integer.parseInt(parts[5],16), singleton.possibleRandomizedItems));
        Set<String> initItems = new HashSet<>(intToItemSet(Integer.parseInt(parts[6],16), singleton.possibleRandomizedItems));
        Set<String> surfaceItems = new HashSet<>(intToItemSet(Integer.parseInt(parts[7],16), singleton.possibleRandomizedItems));
        Set<String> removedItems = new HashSet<>(intToItemSet(Integer.parseInt(parts[8],16), POSSIBLE_REMOVED_ITEMS));
        String xmailerItem = singleton.possibleRandomizedItems.get(Integer.parseInt(parts[9],16));
        BossDifficulty bossDifficulty = BossDifficulty.values()[Integer.parseInt(parts[10],16)];
        int minRandomRemovedItems = Integer.parseInt(parts[11],16);
        int maxRandomRemovedItems = Integer.parseInt(parts[12],16);

        setStartingSeed(seed);
        setEnabledGlitches((List<String>) glitches, true);
        setEnabledDamageBoosts((List<String>) dboosts, true);
        setNonRandomizedItems(nonRandoItems, true);
        setInitiallyAccessibleItems(initItems, true);
        setSurfaceItems(surfaceItems, true);
        setRemovedItems(removedItems, true);
        setXmailerItem(xmailerItem, true);
        setBossDifficulty(bossDifficulty.toString(), true);
        setMinRandomRemovedItems(minRandomRemovedItems, true);
        setMaxRandomRemovedItems(maxRandomRemovedItems, true);

        JOptionPane.showMessageDialog(null, "Settings successfully imported");
    }
}
