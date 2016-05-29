/*
 * PinZhuYin.java
 *
 * Created on July 25, 2007, 4:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pinming.conversions;

import pinming.input.PhoneticTranslator;

import java.util.TreeMap;

/**
 *
 * @author OfficeRecorder
 */
public class PinZhuYin {

    private static final String[] PINYIN = {"A","AI","AN","ANG","AO","BA","BAI","BAN","BANG","BAO","BEI","BEN","BENG","BI","BIAN","BIAO","BIE","BIN","BING","BO","BU","CA","CAI","CAN","CANG","CAO","CE","CEN","CENG","CI","CONG","COU","CU","CUAN","CUI","CUN","CUO","CHA","CHAI","CHAN","CHANG","CHAO","CHE","CHEN","CHENG","CHI","CHONG","CHOU","CHU","CHUA","CHUAI","CHUAN","CHUANG","CHUI","CHUN","CHUO","DA","DAI","DAN","DANG","DAO","DE","DEI","DENG","DI","DIAN","DIAO","DIE","DING","DIU","DONG","DOU","DU","DUAN","DUI","DUN","DUO","E","EI","EN","ENG","ER","FA","FAN","FANG","FEI","FEN","FENG","FO","FOU","FU","GA","GAI","GAN","GANG","GAO","GE","GEI","GEN","GENG","GONG","GOU","GU","GUA","GUAI","GUAN","GUANG","GUI","GUN","GUO","HA","HAI","HAN","HANG","HAO","HE","HEI","HEN","HENG","HONG","HOU","HU","HUA","HUAI","HUAN","HUANG","HUI","HUN","HUO","JI","JIA","JIAN","JIANG","JIAO","JIE","JIN","JING","JIONG","JIU","JU","JUAN","JUE","JUN","KA","KAI","KAN","KANG","KAO","KE","KEN","KENG","KONG","KOU","KU","KUA","KUAI","KUAN","KUANG","KUI","KUN","KUO","LA","LAI","LAN","LANG","LAO","LE","LEI","LENG","LI","LIA","LIAN","LIANG","LIAO","LIE","LIN","LING","LIU","LONG","LOU","LU","LUAN","L\u00dcE","LUN","LUO","L\u00dc","L\u00dcAN","L\u00dcN","MA","MAI","MAN","MANG","MAO","ME","MEI","MEN","MENG","MI","MIAN","MIAO","MIE","MIN","MING","MIU","MO","MOU","MU","NA","NAI","NAN","NANG","NAO","NE","NEI","NEN","NENG","NI","NIAN","NIANG","NIAO","NIE","NIN","NING","NIU","NONG","NOU","NU","NUAN","NUE","NUO","N\u00dc","OU","PA","PAI","PAN","PANG","PAO","PEI","PEN","PENG","PI","PIAN","PIAO","PIE","PIN","PING","PO","POU","PU","QI","QIA","QIAN","QIANG","QIAO","QIE","QIN","QING","QIONG","QIU","QU","QUAN","QUE","QUN","RAN","RANG","RAO","RE","REN","RENG","RI","RONG","ROU","RU","RUAN","RUI","RUN","RUO","SA","SAI","SAN","SANG","SAO","SE","SEN","SENG","SI","SONG","SOU","SU","SUAN","SUI","SUN","SUO","SHA","SHAI","SHAN","SHANG","SHAO","SHE","SHEI","SHEN","SHENG","SHI","SHOU","SHU","SHUA","SHUAI","SHUAN","SHUANG","SHUI","SHUN","SHUO","TA","TAI","TAN","TANG","TAO","TE","TENG","TI","TIAN","TIAO","TIE","TING","TONG","TOU","TU","TUAN","TUI","TUN","TUO","WA","WAI","WAN","WANG","WEI","WEN","WENG","WO","WU","XI","XIA","XIAN","XIANG","XIAO","XIE","XIN","XING","XIONG","XIU","XU","XUAN","XUE","XUN","YA","YAI","YAN","YANG","YAO","YE","YI","YIN","YING","YONG","YOU","YU","YUAN","YUE","YUN","ZA","ZAI","ZAN","ZANG","ZAO","ZE","ZEI","ZEN","ZENG","ZI","ZONG","ZOU","ZU","ZUAN","ZUI","ZUN","ZUO","ZHA","ZHAI","ZHAN","ZHANG","ZHAO","ZHE","ZHEI","ZHEN","ZHENG","ZHI","ZHONG","ZHOU","ZHU","ZHUA","ZHUAI","ZHUAN","ZHUANG","ZHUI","ZHUN","ZHUO"};
    private static final String[] ZHUYIN = {"\u311a","\u311e","\u3122","\u3124","\u3120","\u3105\u311a","\u3105\u311e","\u3105\u3122","\u3105\u3124","\u3105\u3120","\u3105\u311f","\u3105\u3123","\u3105\u3125","\u3105\u3127","\u3105\u3127\u3122","\u3105\u3127\u3120","\u3105\u3127\u311d","\u3105\u3127\u3123","\u3105\u3127\u3125","\u3105\u311b","\u3105\u3128","\u3118\u311a","\u3118\u311e","\u3118\u3122","\u3118\u3124","\u3118\u3120","\u3118\u311c","\u3118\u3123","\u3118\u3125","\u3118","\u3118\u3128\u3125","\u3118\u3121","\u3118\u3128","\u3118\u3128\u3122","\u3118\u3128\u311f","\u3118\u3128\u3123","\u3118\u3128\u311b","\u3114\u311a","\u3114\u311e","\u3114\u3122","\u3114\u3124","\u3114\u3120","\u3114\u311c","\u3114\u3123","\u3114\u3125","\u3114","\u3114\u3128\u3125","\u3114\u3121","\u3114\u3128","\u3114\u3128\u311a","\u3114\u3128\u311e","\u3114\u3128\u3122","\u3114\u3128\u3124","\u3114\u3128\u311f","\u3114\u3128\u3123","\u3114\u3128\u311b","\u3109\u311a","\u3109\u311e","\u3109\u3122","\u3109\u3124","\u3109\u3120","\u3109\u311c","\u3109\u311f","\u3109\u3125","\u3109\u3127","\u3109\u3127\u3122","\u3109\u3127\u3120","\u3109\u3127\u311d","\u3109\u3127\u3125","\u3109\u3127\u3121","\u3109\u3128\u3125","\u3109\u3121","\u3109\u3128","\u3109\u3128\u3122","\u3109\u3128\u311f","\u3109\u3128\u3123","\u3109\u3128\u311b","\u311c","\u311f","\u3123","\u3125","\u3126","\u3108\u311a","\u3108\u3122","\u3108\u3124","\u3108\u311f","\u3108\u3123","\u3108\u3125","\u3108\u311b","\u3108\u3121","\u3108\u3128","\u310d\u311a","\u310d\u311e","\u310d\u3122","\u310d\u3124","\u310d\u3120","\u310d\u311c","\u310d\u311f","\u310d\u3123","\u310d\u3125","\u310d\u3128\u3125","\u310d\u3121","\u310d\u3128","\u310d\u3128\u311a","\u310d\u3128\u311e","\u310d\u3128\u3122","\u310d\u3128\u3124","\u310d\u3128\u311f","\u310d\u3128\u3123","\u310d\u3128\u311b","\u310f\u311a","\u310f\u311e","\u310f\u3122","\u310f\u3124","\u310f\u3120","\u310f\u311c","\u310f\u311f","\u310f\u3123","\u310f\u3125","\u310f\u3128\u3125","\u310f\u3121","\u310f\u3128","\u310f\u3128\u311a","\u310f\u3128\u311e","\u310f\u3128\u3122","\u310f\u3128\u3124","\u310f\u3128\u311f","\u310f\u3128\u3123","\u310f\u3128\u311b","\u3110\u3127","\u3110\u3127\u311a","\u3110\u3127\u3122","\u3110\u3127\u3124","\u3110\u3127\u3120","\u3110\u3127\u311d","\u3110\u3127\u3123","\u3110\u3127\u3125","\u3110\u3129\u3125","\u3110\u3127\u3121","\u3110\u3129","\u3110\u3129\u3122","\u3110\u3129\u311d","\u3110\u3129\u3123","\u310e\u311a","\u310e\u311e","\u310e\u3122","\u310e\u3124","\u310e\u3120","\u310e\u311c","\u310e\u3123","\u310e\u3125","\u310e\u3128\u3125","\u310e\u3121","\u310e\u3128","\u310e\u3128\u311a","\u310e\u3128\u311e","\u310e\u3128\u3122","\u310e\u3128\u3124","\u310e\u3128\u311f","\u310e\u3128\u3123","\u310e\u3128\u311b","\u310c\u311a","\u310c\u311e","\u310c\u3122","\u310c\u3124","\u310c\u3120","\u310c\u311c","\u310c\u311f","\u310c\u3125","\u310c\u3127","\u310c\u3127\u311a","\u310c\u3127\u3122","\u310c\u3127\u3124","\u310c\u3127\u3120","\u310c\u3127\u311d","\u310c\u3127\u3123","\u310c\u3127\u3125","\u310c\u3127\u3121","\u310c\u3128\u3125","\u310c\u3121","\u310c\u3128","\u310c\u3128\u3122","\u310c\u3129\u311d","\u310c\u3128\u3123","\u310c\u3128\u311b","\u310c\u3129","\u310c\u3129\u3122","\u310c\u3129\u3123","\u3107\u311a","\u3107\u311e","\u3107\u3122","\u3107\u3124","\u3107\u3120","\u3107\u311c","\u3107\u311f","\u3107\u3123","\u3107\u3125","\u3107\u3127","\u3107\u3127\u3122","\u3107\u3127\u3120","\u3107\u3127\u311d","\u3107\u3127\u3123","\u3107\u3127\u3125","\u3107\u3127\u3121","\u3107\u311b","\u3107\u3121","\u3107\u3128","\u310b\u311a","\u310b\u311e","\u310b\u3122","\u310b\u3124","\u310b\u3120","\u310b\u311c","\u310b\u311f","\u310b\u3123","\u310b\u3125","\u310b\u3127","\u310b\u3127\u3122","\u310b\u3127\u3124","\u310b\u3127\u3120","\u310b\u3127\u311d","\u310b\u3127\u3123","\u310b\u3127\u3125","\u310b\u3127\u3121","\u310b\u3128\u3125","\u310b\u3121","\u310b\u3128","\u310b\u3128\u3122","\u310b\u3129\u311d","\u310b\u3128\u311b","\u310b\u3129","\u3121","\u3106\u311a","\u3106\u311e","\u3106\u3122","\u3106\u3124","\u3106\u3120","\u3106\u311f","\u3106\u3123","\u3106\u3125","\u3106\u3127","\u3106\u3127\u3122","\u3106\u3127\u3120","\u3106\u3127\u311d","\u3106\u3127\u3123","\u3106\u3127\u3125","\u3106\u311b","\u3106\u3121","\u3106\u3128","\u3111\u3127","\u3111\u3127\u311a","\u3111\u3127\u3122","\u3111\u3127\u3124","\u3111\u3127\u3120","\u3111\u3127\u311d","\u3111\u3127\u3123","\u3111\u3127\u3125","\u3111\u3129\u3125","\u3111\u3127\u3121","\u3111\u3129","\u3111\u3129\u3122","\u3111\u3129\u311d","\u3111\u3129\u3123","\u3116\u3122","\u3116\u3124","\u3116\u3120","\u3116\u311c","\u3116\u3123","\u3116\u3125","\u3116","\u3116\u3128\u3125","\u3116\u3121","\u3116\u3128","\u3116\u3128\u3122","\u3116\u3128\u311f","\u3116\u3128\u3123","\u3116\u3128\u311b","\u3119\u311a","\u3119\u311e","\u3119\u3122","\u3119\u3124","\u3119\u3120","\u3119\u311c","\u3119\u3123","\u3119\u3125","\u3119","\u3119\u3128\u3125","\u3119\u3121","\u3119\u3128","\u3119\u3128\u3122","\u3119\u3128\u311f","\u3119\u3128\u3123","\u3119\u3128\u311b","\u3115\u311a","\u3115\u311e","\u3115\u3122","\u3115\u3124","\u3115\u3120","\u3115\u311c","\u3115\u311f","\u3115\u3123","\u3115\u3125","\u3115","\u3115\u3121","\u3115\u3128","\u3115\u3128\u311a","\u3115\u3128\u311e","\u3115\u3128\u3122","\u3115\u3128\u3124","\u3115\u3128\u311f","\u3115\u3128\u3123","\u3115\u3128\u311b","\u310a\u311a","\u310a\u311e","\u310a\u3122","\u310a\u3124","\u310a\u3120","\u310a\u311c","\u310a\u3125","\u310a\u3127","\u310a\u3127\u3122","\u310a\u3127\u3120","\u310a\u3127\u311d","\u310a\u3127\u3125","\u310a\u3128\u3125","\u310a\u3121","\u310a\u3128","\u310a\u3128\u3122","\u310a\u3128\u311f","\u310a\u3128\u3123","\u310a\u3128\u311b","\u3128\u311a","\u3128\u311e","\u3128\u3122","\u3128\u3124","\u3128\u311f","\u3128\u3123","\u3128\u3125","\u3128\u311b","\u3128","\u3112\u3127","\u3112\u3127\u311a","\u3112\u3127\u3122","\u3112\u3127\u3124","\u3112\u3127\u3120","\u3112\u3127\u311d","\u3112\u3127\u3123","\u3112\u3127\u3125","\u3112\u3129\u3125","\u3112\u3127\u3121","\u3112\u3129","\u3112\u3129\u3122","\u3112\u3129\u311d","\u3112\u3129\u3123","\u3127\u311a","\u3127\u311e","\u3127\u3122","\u3127\u3124","\u3127\u3120","\u3127\u311d","\u3127","\u3127\u3123","\u3127\u3125","\u3129\u3125","\u3127\u3121","\u3129","\u3129\u3122","\u3129\u311d","\u3129\u3123","\u3117\u311a","\u3117\u311e","\u3117\u3122","\u3117\u3124","\u3117\u3120","\u3117\u311c","\u3117\u311f","\u3117\u3123","\u3117\u3125","\u3117","\u3117\u3128\u3125","\u3117\u3121","\u3117\u3128","\u3117\u3128\u3122","\u3117\u3128\u311f","\u3117\u3128\u3123","\u3117\u3128\u311b","\u3113\u311a","\u3113\u311e","\u3113\u3122","\u3113\u3124","\u3113\u3120","\u3113\u311c","\u3113\u311f","\u3113\u3123","\u3113\u3125","\u3113","\u3113\u3128\u3125","\u3113\u3121","\u3113\u3128","\u3113\u3128\u311a","\u3113\u3128\u311e","\u3113\u3128\u3122","\u3113\u3128\u3124","\u3113\u3128\u311f","\u3113\u3128\u3123","\u3113\u3128\u311b"};
    
    private static TreeMap<String,String> pinZhu, zhuPin;
    
    /** Creates a new instance of PinZhuYin */
    private PinZhuYin() {}
    
    public static void initialize() {
        pinZhu = new TreeMap<String,String>();
        zhuPin = new TreeMap<String,String>();
        for (int i=0;i<PINYIN.length;i++) {
            pinZhu.put(PINYIN[i],ZHUYIN[i]);
            zhuPin.put(ZHUYIN[i],PINYIN[i]);
        }
    }
    
    public static String toZhuYin(String pinyin) {
        return pinZhu.get(pinyin);
    }
    
    public static String toPinYin(String zhuyin) {
        return zhuPin.get(zhuyin);
    }
    
    public static char[] toZhuYinArray(String pinyin) {
        char[] result = {0,0,0};
        String zhuyinStr = toZhuYin(pinyin);
        if (zhuyinStr != null) {
            char thisChar;
            for (int i=0;i<zhuyinStr.length();i++) {
                thisChar = zhuyinStr.charAt(i);
                if (thisChar < PhoneticTranslator.ZY_A) {
                    result[0] = thisChar;
                }
                else if (thisChar < PhoneticTranslator.ZY_I) {
                    result[2] = thisChar;
                }
                else {
                    result[1] = thisChar;
                }
            }
        }
        return result;
    }
    
}

