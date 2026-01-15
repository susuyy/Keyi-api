package com.ht.note.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Data
public class AphorismUtil implements Serializable {

    public static final List<String> SOUPS;

    public static final Map<String,String> AphorismMap = new HashMap<>();

    static {
        // 初始化静态属性
        SOUPS = new ArrayList<>();
        SOUPS.add("成功的秘诀在于坚持自己的信念。 - 迈克尔·乔丹");
        SOUPS.add("改变你的想法，改变你的世界。 - 诺曼·文森特·皮尔");
        SOUPS.add("只有行动才能改变命运。 - 奥黛丽·赫本");
        SOUPS.add("勇气并非没有恐惧，而是面对恐惧后仍然前行。 - 纳尔逊·曼德拉");
        SOUPS.add("失败乃成功之母。 - 高尔基");
        SOUPS.add("将梦想交给时间，它会让你实现。 - 约翰·列侬");
        SOUPS.add("成功不是得到别人的认可，而是成为最好的自己。 - 莱昂纳多·达·芬奇");
        SOUPS.add("敢于冒险，你才能发现新的世界。 - 奥德修斯");
        SOUPS.add("每一次努力，都是改变自己的机会。 - 爱因斯坦");
        SOUPS.add("追随自己的梦想，让内心闪耀光芒。 - 华特·迪士尼");
        SOUPS.add("天空从不留下翅膀，给一直飞翔的人。 - 戴尔·卡耐基");
        SOUPS.add("希望是生命的早餐。 - 伦纳德·科恩");
        SOUPS.add("你唯一需要害怕的就是害怕本身。 - 密歇尔·奥巴马");
        SOUPS.add("只有那些从来不放弃追求的人，才能够实现梦想。 - 斯蒂芬妮·迈尔");
        SOUPS.add("向前看，不要回头。你的最好时光在未来等待。 - 罗伯特·弗罗斯特");
        SOUPS.add("我们必须接受失望，因为它是有限的。然后再次拥抱希望，因为它是无穷的。 - 马丁·路德·金");
        SOUPS.add("你的时间有限，所以不要浪费时间活在别人的生活中。 - 史蒂夫·乔布斯");
        SOUPS.add("成功并非最终目的地，而是永不停歇的旅程。 - 比尔·盖茨");
        SOUPS.add("不要停止学习，因为生活永远是一个持续的教育过程。 - 基辛格");
        SOUPS.add("不要让过去的阴影掩盖住你看到阳光的眼睛。 - 奥普拉·温弗瑞");
        SOUPS.add("每一个拥抱失败的人都在接近成功的边缘。 - 汤姆·霍华德");
        SOUPS.add("成功的关键是将你累积的知识和经验投入到工作中。 - 赛琳娜·戈麦斯");
        SOUPS.add("不管做什么事情，始终保持你的激情。 - 马克·扎克伯格");
        SOUPS.add("勇敢去追求自己的梦想，而不是他人对你的期待。 - 乔布·洛布");
        SOUPS.add("只有不断超越自己，才能发现自己无限的潜力。 - 戴夫·格洛弗");
        SOUPS.add("生活从不给予你想要的，只会给你你所付出的。 - 斯通");
        SOUPS.add("不要让你的过去阻碍你的未来。 - 米歇尔·奥巴马");
        SOUPS.add("没有人可以回到过去，重新开始。但是，谁都可以从现在开始，创造一个全新的结局。 - 卡尔·巴克");
        SOUPS.add("不要抱怨风暴，学会航行。 - 路易斯·帕斯特尔");
        SOUPS.add("你是唯一能改变自己的人。 - 罗伯特·福特");
        SOUPS.add("生活中最重要的不是找到自己，而是创造出自己。 - 乔治·伯纳德·肖");
        SOUPS.add("真正的力量来自内心的平和。 - 布鲁斯·李");
        SOUPS.add("当你失去一切的时候，你才能真正开始拥有一切。 - 布拉德·皮特");
        SOUPS.add("每一次挫折都是成功的前奏。 - 查尔斯·史维布");
        SOUPS.add("成功的关键在于相信自己能够成功。 - 史蒂芬·霍金");
        SOUPS.add("一个人的价值不是由外界的评判来决定的，而是由自己内心的评判来决定的。 - 爱默生");
        SOUPS.add("永远不要停下探索未知的步伐。 - 爱丽丝·沃克");
        SOUPS.add("没有什么可以阻挡一个有明确目标的人。 - 坦尼希尔");
        SOUPS.add("只有不断拓展自己，才能够超越自己。 - 梅拉尼娅·特朗普");
        SOUPS.add("不要等待机会，而要自己创造机会。 - 乔治·巴顿");
        SOUPS.add("成功的秘诀就是决心和毅力。 - 雷曼德·哈立勒");
        SOUPS.add("人生最大的失败就是从未尝试。 - 毛姆");
        SOUPS.add("活着就是为了改变世界。 - 纳尔逊·曼德拉");
        SOUPS.add("对于积极的人来说，每一次困境都是一个机会。 - 切斯特·本宁顿");
        SOUPS.add("坚持不懈地努力，你将收获美好的未来。 - 查尔斯·史维布");
        SOUPS.add("只有梦想者才能看到彩虹的另一端。 - 罗伯特·赫谢");
        SOUPS.add("无论何时何地，永远相信你自己。 - 马拉拉·优素福扎伊");
        SOUPS.add("你是你生活的艺术家，只要你愿意，可以随时画出新的画面。 - 特蕾莎修女");
        SOUPS.add("当你的一切都在发生改变时，那就是你改变的机会。 - 杰基·乔伊纳");
        SOUPS.add("成功在于挑战自己并乐在其中。 - 玛丽莲·梦露");
        SOUPS.add("即使人生有无数可能的选择，但只有你选择的那个才是最好的。 - 奥普拉·温弗瑞");
        SOUPS.add("当你面对恐惧时，你就超越了它。 - 杰米·福克斯");
        SOUPS.add("成功是一种心态，不是一种结果。 - 威尔·史密斯");
        SOUPS.add("相信自己能够成为你想成为的人。 - 伯纳德·肖");
        SOUPS.add("每一次失败都是离成功更近一步。 - 迈克尔·乔丹");
        SOUPS.add("不要等待别人认可你的价值，而是要自己定义自己的价值。 - 比尔·盖茨");
        SOUPS.add("虽然世界不完美，但我们可以努力将其变得更好。 - 奥巴马");
        SOUPS.add("勇敢去面对你所拥有的一切，并感恩。 - 黛米·洛瓦托");
        SOUPS.add("你不能改变过去，但你可以改变未来。 - 雷·刘易斯");
        SOUPS.add("相信自己，你已经走过了比你想象的更远的路。 - 杰克·伦敦");
        SOUPS.add("成功与否取决于你的信念。 - 布鲁斯·李");
        SOUPS.add("每一份付出都会得到回报，只要你不放弃。 - 雅克·库斯托");
        SOUPS.add("愿你有足够的勇气去改变自己的生活。 - 约翰·肯尼迪");
        SOUPS.add("相信自己的内心，跟随你的梦想。 - 密歇尔·奥巴马");
        SOUPS.add("真正的力量不是来自外部，而是来自你内心的坚持。 - 雷·刘易斯");
        SOUPS.add("相信你自己，你能够超越你对自己的想象。 - 奥普拉·温弗瑞");
        SOUPS.add("成功的关键是寻找到你热爱的事业，并投入全部精力。 - 艾伦·图灵");
        SOUPS.add("只有当你跨出舒适区，你才能发现自己的无限可能。 - 琼·C·鲍威尔");
        SOUPS.add("唯有希望，才能引领我们走向更美好的未来。 - 亚伯拉罕·林肯");
        SOUPS.add("不管你身在何处，也不管你处境如何，你都可以选择成为自己想成为的人。 - 斯蒂芬·霍金");
        SOUPS.add("不要抱怨生活，因为它从不亏待任何人。 - 玛丽莲·梦露");
        SOUPS.add("成功是一个阶梯，你必须一步一个脚印地走完每一级。 - 麦克阿瑟将军");
        SOUPS.add("向前看，不要回头。你的未来在等待着你。 - 华特·迪士尼");
        SOUPS.add("只有那些勇于冒险的人才能够收获幸福。 - 巴贝特");
        SOUPS.add("行动是成功的桥梁，愿望在机遇中萌芽。 - 卡拉姆·埃利亚斯");
        SOUPS.add("做自己热爱的事情，并乐在其中。 - 迈克尔·乔丹");
        SOUPS.add("你不需要等待别人的认可，相信你自己的力量。 - 赛琳娜·戈麦斯");
        SOUPS.add("努力奋斗，成功将会与你相遇。 - 坦尼");
        // 添加更多元素...

        //初始化推送鸡汤
        int min = 1; // 最小值（包含）
        int max = SOUPS.size(); // 最大值（包含）
        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;

        String yearMoonDay = DateStrUtil.nowDateStrYearMoonDay();
        AphorismMap.put(yearMoonDay,SOUPS.get(randomNumber));

    }




}
