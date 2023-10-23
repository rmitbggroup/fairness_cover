package experiment;

import entity.ExpSetting;

import java.util.ArrayList;

public class ExpPlan {

    ArrayList<ExpSetting> expPlanList;
    ExpSetting defaultSetting = new ExpSetting();

    public ExpPlan() {
        expPlanList = new ArrayList<>();
        setDefaultSetting();
        //avg inf = 20% - 1%
        expPlanList.add(exp01());
        expPlanList.add(exp02());
        expPlanList.add(exp03());
        expPlanList.add(exp04());
        expPlanList.add(exp05());

        expPlanList.add(exp11());
        expPlanList.add(exp12());
        expPlanList.add(exp13());
        expPlanList.add(exp14());
        expPlanList.add(exp15());

        expPlanList.add(exp21());
        expPlanList.add(exp22());
        expPlanList.add(exp23());
        expPlanList.add(exp24());
        expPlanList.add(exp25());

        expPlanList.add(exp31());
        expPlanList.add(exp32());
        expPlanList.add(exp33());
        expPlanList.add(exp34());
        expPlanList.add(exp35());

        expPlanList.add(exp41());
        expPlanList.add(exp42());
        expPlanList.add(exp43());
        expPlanList.add(exp44());
        expPlanList.add(exp45());

        //lambda
        expPlanList.add(exp51());
        expPlanList.add(exp52());
        expPlanList.add(exp53());
        expPlanList.add(exp54());

        //theta
        expPlanList.add(exp61());
        expPlanList.add(exp62());
        expPlanList.add(exp63());
        expPlanList.add(exp64());
        expPlanList.add(exp65());

        //theta
        expPlanList.add(exp71());
        expPlanList.add(exp72());
        expPlanList.add(exp73());
        expPlanList.add(exp74());
        expPlanList.add(exp75());


    }

    private void setDefaultSetting() {
        defaultSetting.trajPer = .1;
        defaultSetting.multiBillboard = 1;
        defaultSetting.beta = .1;
        defaultSetting.gamma = .5;
        defaultSetting.infProb = .33;
        defaultSetting.lambda = 100;
        defaultSetting.model = 1;
        defaultSetting.range = "";
        defaultSetting.algs = new int[]{1, 2, 9, 7};
        defaultSetting.test = true;
        defaultSetting.randomBudget = true;
        defaultSetting.infPercentage = 1;
        defaultSetting.startPayment = 5;
    }

    public ArrayList<ExpSetting> getExpPlanList() {
        return expPlanList;
    }

    private ExpSetting exp01() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1.2;
        exp1.startPayment = 20;
        return exp1;
    }

    private ExpSetting exp02() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1;
        exp1.startPayment = 20;
        return exp1;
    }

    private ExpSetting exp03() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 0.8;
        exp1.startPayment = 20;
        return exp1;
    }

    private ExpSetting exp04() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .6;
        exp1.startPayment = 20;
        return exp1;
    }

    private ExpSetting exp05() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .4;
        exp1.startPayment = 20;
        return exp1;
    }

    private ExpSetting exp11() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1.2;
        exp1.startPayment = 10;
        return exp1;
    }

    private ExpSetting exp12() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1;
        exp1.startPayment = 10;
        return exp1;
    }

    private ExpSetting exp13() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 0.8;
        exp1.startPayment = 10;
        return exp1;
    }

    private ExpSetting exp14() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .6;
        exp1.startPayment = 10;
        return exp1;
    }

    private ExpSetting exp15() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .4;
        exp1.startPayment = 10;
        return exp1;
    }

    private ExpSetting exp21() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1.2;
        exp1.startPayment = 5;
        return exp1;
    }

    private ExpSetting exp22() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1;
        exp1.startPayment = 5;
        return exp1;
    }

    private ExpSetting exp23() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 0.8;
        exp1.startPayment = 5;
        return exp1;
    }

    private ExpSetting exp24() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .6;
        exp1.startPayment = 5;
        return exp1;
    }

    private ExpSetting exp25() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .4;
        exp1.startPayment = 5;
        return exp1;
    }

    private ExpSetting exp31() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1.2;
        exp1.startPayment = 2;
        return exp1;
    }

    private ExpSetting exp32() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1;
        exp1.startPayment = 2;
        return exp1;
    }

    private ExpSetting exp33() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 0.8;
        exp1.startPayment = 2;
        return exp1;
    }

    private ExpSetting exp34() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .6;
        exp1.startPayment = 2;
        return exp1;
    }

    private ExpSetting exp35() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .4;
        exp1.startPayment = 2;
        return exp1;
    }

    private ExpSetting exp41() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1.2;
        exp1.startPayment = 1;
        return exp1;
    }

    private ExpSetting exp42() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 1;
        exp1.startPayment = 1;
        return exp1;
    }

    private ExpSetting exp43() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = 0.8;
        exp1.startPayment = 1;
        return exp1;
    }

    private ExpSetting exp44() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .6;
        exp1.startPayment = 1;
        return exp1;
    }

    private ExpSetting exp45() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infPercentage = .4;
        exp1.startPayment = 1;
        return exp1;
    }

    private ExpSetting exp51() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.lambda = 50;
        return exp1;
    }

    private ExpSetting exp52() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.lambda = 100;
        return exp1;
    }

    private ExpSetting exp53() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.lambda = 150;
        return exp1;
    }

    private ExpSetting exp54() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.lambda = 200;
        return exp1;
    }

    private ExpSetting exp61() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infProb = .1;
        return exp1;
    }

    private ExpSetting exp62() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infProb = .33;
        return exp1;
    }

    private ExpSetting exp63() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infProb = .5;
        return exp1;
    }

    private ExpSetting exp64() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infProb = .66;
        return exp1;
    }

    private ExpSetting exp65() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.infProb = 1;
        return exp1;
    }

    private ExpSetting exp71() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.gamma = 0;
        return exp1;
    }

    private ExpSetting exp72() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.gamma = 0.25;
        return exp1;
    }

    private ExpSetting exp73() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.gamma = 0.5;
        return exp1;
    }

    private ExpSetting exp74() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.gamma = .75;
        return exp1;
    }

    private ExpSetting exp75() {
        ExpSetting exp1 = new ExpSetting(defaultSetting);
        exp1.gamma = 1;
        return exp1;
    }
}
