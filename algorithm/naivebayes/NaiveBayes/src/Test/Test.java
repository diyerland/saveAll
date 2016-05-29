package Test;

import auxiliary.DataSet;
import auxiliary.Evaluation;
import auxiliary.NaiveBayes;

/**
 *
 * @author daq
 */
public class Test {

    public static void main(String[] args) {
//        String[] dataPaths = new String[]{"breast-cancer.data", "segment.data"};
//        for (String path : dataPaths) {
//            DataSet dataset = new DataSet(path);
////
////            // conduct 10-cv
//           Evaluation eva = new Evaluation(dataset, "NaiveBayes");
//          eva.crossValidation();
////
////      // print mean and standard deviation of accuracy
//      System.out.println("Dataset:" + path + ", mean and standard deviation of accuracy:" + eva.getAccMean() + "," + eva.getAccStd());
//        }
        //本例改为医院隐形眼镜的状况和患者之间的关系
        //contactLensData[0]={1, 1, 1, 1}
        //第一个1代表患者年龄：1:年轻 2:中年 3:老年
        //第二个1代表使用眼镜：1：近视 2:远视
        //第三个1代表散光：1:不是散光 2:散光
        //第四个1代表影响流眼泪：1:流泪减少 2:流泪正常
        double[][] contactLensData = {
                {1, 1, 1, 1}, {1, 1, 1, 2}, {1, 1, 2, 1}, {1, 1, 2, 2},
                {1, 2, 1, 1}, {1, 2, 1, 2}, {1, 2, 2, 1}, {1, 2, 2, 2},
                {2, 1, 1, 1}, {2, 1, 1, 2}, {2, 1, 2, 1}, {2, 1, 2, 2},
                {2, 2, 1, 1}, {2, 2, 1, 2}, {2, 2, 2, 1}, {2, 2, 2, 2},
                {3, 1, 1, 1}, {3, 1, 1, 2}, {3, 1, 2, 1}, {3, 1, 2, 2},
                {3, 2, 1, 1}, {3, 2, 1, 2}, {3, 2, 2, 1}, {3, 2, 2, 2}
        };

        //分类结果：1:应该使用硬性隐形眼镜 2:应该使用软性隐形眼镜 3:患者不适合带隐形眼镜
        double[] classificationData = {
                3, 2, 3, 1,
                3, 2, 3, 1,
                3, 2, 3, 1,
                3, 2, 3, 3,
                3, 3, 3, 1,
                3, 2, 3, 3
        };

        DataSet dataset = new DataSet(null);
        dataset.setIsCategory(new boolean[]{true,true,true,true});//四个属性都是离散型数据
        dataset.setFeatures(contactLensData);
        dataset.setLabels(classificationData);//标签或者分类
        dataset.setNumAttributes(4);
        dataset.setNumInstnaces(24);
//        Evaluation eva = new Evaluation(dataset, "NaiveBayes");
//        eva.crossValidation();
        NaiveBayes naiveBayes = new NaiveBayes();
        naiveBayes.train(dataset.getIsCategory(),dataset.getFeatures(),dataset.getLabels());
        double result = naiveBayes.predict(new double[]{3, 2, 1, 1});
        double r = result;
    }
}
