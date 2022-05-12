//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.template.chip.controller.test.test2;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum IndustrySecond {
    ELECTRONICS(1, 1, "电子", 1),
    HARDWARE(1, 2, "计算机硬件", 2),
    COMMUNICATION(1, 3, "通信工程", 3),
    REAL_ESTATE_SERVICE(2, 4, "房产中介", 1),
    REAL_ESTATE_INTERMEDIARY(2, 5, "房地产服务", 2),
    CONSTRUCTION_INSTALLATION(2, 6, "建筑安装", 3),
    EQUIPMENT_ENGINEER(2, 7, "设备工程", 4),
    DECORATION_DESIGN(2, 8, "装修设计", 5),
    TESTING_CERTIFICATION(3, 9, "检测认证", 1),
    TURISTHOTELLET_RESTAURANT(3, 10, "旅游酒店餐饮", 2),
    OTHER_SERVICE(3, 11, "其他服务", 3),
    OUTSOURCING_SERVICE(3, 12, "外包服务", 4),
    INFORMATION_SERVICE(3, 13, "信息服务", 5),
    ENTERTAINMENT_LEISURE(3, 14, "娱乐休闲", 6),
    JOIN(3, 15, "招商加盟", 7),
    PROFESSIONAL_CONSULTATION(3, 16, "专业咨询", 8),
    RENTAL_SERVICES(3, 17, "租赁服务", 9),
    ADVERTISING_EXHIBITION(4, 18, "广告会展", 1),
    ADVERTISING_DESIGN(4, 19, "广告设计", 2),
    ADVERTISEMENT_MARKETING(4, 20, "广告营销", 3),
    FILM_TELEVISION_MEDIA(4, 21, "影视传媒", 4),
    IT_SERVICE(5, 22, "IT服务", 1),
    INTERNET_BUSINESS(5, 23, "互联网电商", 2),
    BLOCK_CHAIN(5, 24, "区块链", 3),
    ARTIFICIAL_INTELLIGENCE(5, 25, "人工智能", 4),
    SOFTWARE_DEVELOPMENT(5, 26, "软件开发", 5),
    LIVE_VIDEO(5, 27, "视频直播", 6),
    NETWORK_GAME(5, 28, "网络游戏", 7),
    NETWORK_OPERATION(5, 29, "网络运营", 8),
    IMPORT_EXPORT_TRADE(6, 30, "进出口贸易", 1),
    Aerospace(6, 31, "航空航天", 2),
    TRANSPORT_LOGISTICS(6, 32, "运输物流", 3),
    K12_EDUCATION(7, 33, "k12教育", 1),
    DIPLOMA_EDUCATION(7, 34, "学历教育", 2),
    PRESCHOOL_EDUCATION(7, 35, "学前教育", 3),
    ARTISTIC_INTEREST(7, 36, "艺术兴趣", 4),
    LANGUAGE_STUDY_ABROAD(7, 37, "语言留学", 5),
    VOCATIONAL_TRAINING(7, 38, "职业培训", 6),
    Qualification_examination(7, 39, "资格考证", 7),
    INSURANCE(8, 40, "保险", 1),
    ACCOUNTING_AUDITING(8, 41, "会计/审计", 2),
    INVESTMENT(8, 42, "投资", 3),
    TRUST_GUARANTEE_AUCTION_PAWN(8, 43, "信托/担保/拍卖/典当", 4),
    BANK(8, 44, "银行", 5),
    MINING_SMELTING(9, 45, "采掘·冶炼", 1),
    CHEMICAL_INDUSTRY(9, 46, "化工行业", 2),
    ENVIRONMENTAL_PROTECTION_INDUSTRY(9, 47, "环保行业", 3),
    ENERGY_WATER_CONSERVANCY(9, 48, "能源·水利", 4),
    NEW_ENERGY(9, 49, "新能源", 5),
    ANIMAL_HUSBANDRY(10, 50, "畜牧业", 1),
    FORESTRY(10, 51, "林业", 2),
    AGRICULTURE(10, 52, "农业", 3),
    FISHERIES(10, 53, "渔业", 4),
    INDUSTRIAL_AUTOMATION(11, 54, "工业自动化", 1),
    MACHINE_MANUFACTURING(11, 55, "机械制造", 2),
    MOTORCYCLES_MOTORCYCLES(11, 56, "汽车·摩托车", 3),
    PRINTING_PACKAGING_PAPER_MAKING(11, 57, "印刷·包装·造纸", 4),
    RAW_MATERIAL_PROCESSING(11, 58, "原材料加工", 5),
    OFFICE_EQUIPMENT(12, 59, "办公设备", 1),
    CLOTHING_ACCESSORIES(12, 60, "服装服饰", 2),
    HANDICRAFT_JEWELRY_TOYS(12, 61, "工艺品珠宝玩具", 3),
    HOUSEHOLD_APPLIANCE_BUSINESS(12, 62, "家电业", 4),
    FAST_FOOD_PRODUCTS(12, 63, "快消品", 5),
    WHOLESALE_RETAIL(12, 64, "批发零售", 6),
    LUXURY_GOODS_COLLECTION(12, 65, "奢侈品收藏品", 7),
    PUBLIC_HOSPITALS(13, 66, "公立医院", 1),
    SCIENTIFIC_RESEARCH_INSTITUTION(13, 67, "科研机构", 2),
    SCHOOL(13, 68, "学校", 3),
    GOVERNMENT_ORGANS(13, 69, "政府机构", 4),
    BIOLOGICAL_DETECTION(14, 70, "生物检测", 1),
    BIOPHARMACEUTICAL_ENGINEERING(14, 71, "生物制药工程", 2),
    HEALTH_CARE_BEAUTY(14, 72, "医疗保健·美容", 3),
    MEDICAL_APPARATUS_INSTRUMENTS(14, 73, "医疗器械", 4),
    CALL_CENTER(3, 74, "呼叫中心", 10),
    WUYE_DESIGN(2, 75, "物业管理", 6),
    S4_DESIGN(11, 76, "汽车4S店", 6),
    RLZYWB_DESIGN(3, 77, "人力资源外包", 11),
    CWWB_DESIGN(3, 78, "财税外包", 12),
    RJWB_DESIGN(3, 79, "软件外包", 13);

    private int parentId;
    private int key;
    private String value;
    private int sort;

    private IndustrySecond(int parentId, int key, String value, int sort) {
        this.parentId = parentId;
        this.key = key;
        this.value = value;
        this.sort = sort;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public static List<Industry> getValues(int parentId) {
        List<Industry> industrySecondList = new ArrayList();
        IndustrySecond[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            IndustrySecond industrySecond = var2[var4];
            if (parentId == industrySecond.parentId) {
                Industry industry = new Industry();
                industry.setKey(industrySecond.key);
                industry.setValue(industrySecond.value);
                industry.setSort(industrySecond.sort);
                industrySecondList.add(industry);
            }
        }

        return (List) industrySecondList.stream().sorted().collect(Collectors.toList());
    }

    public static List<Integer> getSecondValues(int parentId) {
        List<Integer> industrySecondList = new ArrayList();
        IndustrySecond[] var2 = values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            IndustrySecond industrySecond = var2[var4];
            if (parentId == industrySecond.parentId) {
                industrySecondList.add(industrySecond.key);
            }
        }

        return industrySecondList;
    }

    public static IndustrySecond getValueByKey(Integer key) {
        IndustrySecond i = null;
        if (null == key) {
            return null;
        } else {
            IndustrySecond[] var2 = values();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                IndustrySecond industrySecond = var2[var4];
                if (key.equals(industrySecond.key)) {
                    i = industrySecond;
                    break;
                }
            }

            return i;
        }
    }

    public static void main(String[] args) {

        List<Industry> values = IndustryFirst.getValues();
        Map<String, Integer> collect = values.stream().collect(Collectors.toMap(Industry::getValue, Industry::getKey));
        Integer integer = collect.get("能源.化工.环保");
        System.out.println(integer);


//        List<IndustruMap> objects = Lists.newArrayList();
//
//
//        List<Industry> values = IndustryFirst.getValues();
//        values.stream().forEach(e->{
//            int key = e.getKey();
//            List<Industry> values1 = IndustrySecond.getValues(key);
//            List<String> collect = values1.stream().map(Industry::getValue).collect(Collectors.toList());
//            IndustruMap industruMap = new IndustruMap();
//            industruMap.setFirstName(e.getValue());
//            industruMap.setSecondNames(String.join(",",collect));
//            objects.add(industruMap);
//        });
//
//
//        String writePath = "C:\\Users\\EDZ\\Desktop\\data\\result\\行业"+System.currentTimeMillis()+".xlsx";
//        ExcelWriter writer = ExcelUtil.getWriter(writePath);
//        writer.write(objects);
//        writer.close();
    }

    static class IndustruMap {
        private String firstName;
        private String secondNames;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondNames() {
            return secondNames;
        }

        public void setSecondNames(String secondNames) {
            this.secondNames = secondNames;
        }
    }

}
