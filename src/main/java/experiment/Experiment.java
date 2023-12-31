package experiment;

import algorithms.*;
import entity.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class Experiment {

    private ArrayList<ExpSetting> expList;
    private ArrayList<ArrayList<Double>> revenueList;
    private ArrayList<ArrayList<Integer>> billboardNumList;
    private ArrayList<ArrayList<Double>> totalCostList;
    private ArrayList<ArrayList<double[]>> regretList;
    private ArrayList<ArrayList<Integer>> satisfiedNumberList;
    private ArrayList<Advertiser> advertiserList;


    public Experiment() {
        expList = new ArrayList<>();
        revenueList = new ArrayList<>();
        billboardNumList = new ArrayList<>();
        totalCostList = new ArrayList<>();
        regretList = new ArrayList<>();
        satisfiedNumberList = new ArrayList<>();
        ExpPlan expPlan = new ExpPlan();
        expList.addAll(expPlan.getExpPlanList());
    }

    public void run() {
        int expIndex = 0;
        ArrayList<Billboard> billboards;
        //List<Route> routes;
        Initialization initialization = new Initialization();
        ;

        //Doing experiment based on experiment setting list
        for (ExpSetting exp : expList) {
            exp.expIndex = expIndex++;
            loadSetting(exp);
            initialization.setExp(exp);
            initialization.setRanges(exp.range);
            initialization.reload();
            generateAdv(exp, initialization.totalInf);

            //initialization.setRouteAdvNum();
            for (int i = 0; i < exp.algs.length; i++) {
                System.out.println("Experiment " + expList.indexOf(exp) + " - " + i
                        + " start ***********************************************************************************");
                //initialization
                billboards = initialization.getBillboards();
                exp.billboardNumber = billboards.size();
                exp.routeNunber = initialization.getRouteNum();

                if (Setting.test)
                    System.out.println("Initialization finished");

                try {
                    GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(billboards, exp);

                    switch (exp.algs[i]) {
                        case 0:
                            exp.algNames[i] = "Greedy";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            greedyAlgorithm.countTime = true;
                            greedyAlgorithm.greedyForAll();
                            break;
                        case 1:
                            exp.algNames[i] = "G_ForOne";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            greedyAlgorithm.countTime = true;
                            greedyAlgorithm.greedyForOne();
                            break;
                        case 2:
                            exp.algNames[i] = "G_forAll+";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            greedyAlgorithm.countTime = true;
                            greedyAlgorithm.greedyForAllPlus();
                            break;
                        case 5:
                            exp.algNames[i] = "GRASP";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            GRASP grasp = new GRASP(billboards, exp, initialization);
                            grasp.findResult();
                            break;
                        case 7:
                            exp.algNames[i] = "GRASP+BILL";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            GRASP grasp2 = new GRASP(billboards, exp, initialization);
                            grasp2.setLS(true);
                            grasp2.findResult();
                            break;
                        case 9:
                            exp.algNames[i] = "GRASP+ADV";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            GRASP grasp3 = new GRASP(billboards, exp, initialization);
                            grasp3.setLS2(true);
                            grasp3.findResult();
                            break;
                        case 10:
                            exp.algNames[i] = "GRASP+A/B";
                            System.out.println("Algorithm --- " + exp.algNames[i]);
                            GRASP grasp4 = new GRASP(billboards, exp, initialization);
                            grasp4.setLS(true);
                            grasp4.setLS2(true);
                            grasp4.findResult();
                            break;
                    }

                    printResult(exp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            outputExperimentResult(exp);
        }
        printSummary();
    }

    private void outputExperimentResult(ExpSetting expSetting) {
        ArrayList<String> content = new ArrayList<>();
        //AnalyseCenter analyseCenter = new AnalyseCenter();

        content.add("++++++++++++++++++++++ Experiment Results Summary ++++++++++++++++++++++\n\r");
        content.add("City: " + (Setting.fileType == 1 ? "NYC" : "Singapore") + "\n\r");
        content.add("Remove billboard " + Setting.removeThreshold + "    | Influence Probability " + Setting.infProb + "   | Beta(Cost Penalty) " + expSetting.beta + "\n\r");
        content.add("Billboard num " + expSetting.billboardNumber + " (" + Setting.removeThreshold + ")" + "   | Trajectory per " + expSetting.routeNunber + "(" + expSetting.trajPer + ")" + "\n\r");
        content.add("Lambda " + expSetting.lambda + "M   | Random Generator " + expSetting.randomBudget + "    | Model " + expSetting.model + "\n\r");
        content.add("Advertiser num " + expSetting.advNum + " (" + expSetting.infPercentage + ")"
                + "   | Advertiser Avg Inf " + expSetting.startPayment + "%\n\r");
        content.add("------------------------ Experiment " + expSetting.expIndex + " ------------------------\n\r");
        content.add(String.format("%-5s|%-10s|%-15s|%-15s|%-21s|%-10s|%-10s|%-10s|%-10s|%-10s%n", "Alg", "Name", "Billboard Num",
                "Total Cost", "Total Revenue", "R-unsatis", "R-inf", "R-cost", "Satisfied", "Time Cost\n\r"));
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < expSetting.algs.length; i++) {
            line.append(String.format("%-5s|", expSetting.algs[i]));
            line.append(String.format("%-10s|", expSetting.algNames[i]));
            line.append(String.format("%-15s|", billboardNumList.get(expSetting.expIndex).get(i) + "/" + expSetting.billboardNumber));
            line.append(String.format("%-15s|", totalCostList.get(expSetting.expIndex).get(i)));
            line.append(String.format("%-10s/%-10s|", revenueList.get(expSetting.expIndex).get(i), expSetting.totalPayment));
            long used = expSetting.timeCostList.get(i);
            for (double regret : regretList.get(expSetting.expIndex).get(i)) {
                line.append(String.format("%-10s|", Math.floor(regret)));
            }
            line.append(String.format("%-10s|", expList.get(expSetting.expIndex).advNum + "/" + satisfiedNumberList.get(expSetting.expIndex).get(i)));
            line.append(String.format("%-10s|%n", used / 1000.0 + " s \n\r"));
        }
        content.add(line.toString());
        content.add("------------------------------------------------------------------------\n\r");
        /*
        content.add("++++++++++++++++++++++ Experiment Results Detail ++++++++++++++++++++++\n\r");

        int resultIndex = 0;
        for (int n = 0; n < expList.size(); n++) {

            double totalRevenue = 0.0;
            ExpSetting exp = expList.get(n);


            content.add("*************************** Experiment " + n + " ***************************\n\r");

            for (int i = 0; i < expList.get(n).algs.length; i++) {
                content.add("*************************** Algorithm " + i + " ***************************\n\r");
                BillboardSet[] resultSetList = allResult.get(resultIndex++);

                for (int advID = 0; advID < exp.advNum; advID++) {
                    if (exp.resultSetList[advID].getInfluence(true) >= exp.advertierList.get(advID).getRequiredInf()) {
                        content.add("Advertiser ID " + advID + "\n\r");
                        content.add("Required influence " + exp.advertierList.get(advID).getRequiredInf() + "\n\r");
                        content.add("Payment " + exp.advertierList.get(advID).getBudget() + "\n\r");
                        content.add("Billboard List " + resultSetList[advID].getBillboardList() + "\n\r");
                        totalRevenue += exp.advertierList.get(advID).getBudget();
                    }
                    content.add("-----------------------------------------------" + "\n\r");
                }
                content.add("Total Revenue " + totalRevenue + "\n\r");
                content.addAll(analyseCenter.getMeanDeviation(resultSetList));
            }


        }
        */

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        Date date = new Date();
        String fileName = "Experiment " + sdf.format(date);

        OutputExperiment outputExperiment = new OutputExperiment(fileName);
        outputExperiment.writeFile(content);

    }

    private void printSummary() {
        System.out.println("++++++++++++++++++++++ Experiment Results Summary ++++++++++++++++++++++");
        for (int n = 0; n < expList.size(); n++) {
            System.out.println("------------------------ Experiment " + n + " ------------------------");
            System.out.printf("%-12s|%-5s|%-10s|%-15s|%-15s|%-20s|%-15s|%-10s|%-10s%n",
                    "Exp ID", "Alg", "Name", "Billboard Num", "Total Cost", "Total Revenue", "Regret", "Satisfied", "Time Cost");
            for (int i = 0; i < expList.get(n).algs.length; i++) {
                System.out.printf("%-12s|", "Exp ID " + n + "/" + i);
                System.out.printf("%-5s|", expList.get(n).algs[i]);
                System.out.printf("%-10s|", expList.get(n).algNames[i]);
                System.out.printf("%-15s|", billboardNumList.get(n).get(i) + "/" + expList.get(n).billboardNumber);
                System.out.printf("%-15s|", totalCostList.get(n).get(i));
                System.out.printf("%-9s/%-10s|", revenueList.get(n).get(i), expList.get(n).totalPayment);
                double a = 0;
                for (double z : regretList.get(n).get(i)) {
                    a += z;
                }
                System.out.printf("%-15s|", Math.floor(a));
                System.out.printf("%-10s|", expList.get(n).advNum + "/" + satisfiedNumberList.get(n).get(i));
                long used = expList.get(n).timeCostList.get(i);
                System.out.printf("%-10s|%n", used / 1000.0 + " s");
            }
            System.out.println("------------------------------------------------------------------------");
        }

        System.out.println("++++++++++++++++++++++ Experiment Results Summary ++++++++++++++++++++++");
    }

    private void printResult(ExpSetting exp) {
        double totalRevenue = 0.0;
        double totalRegret;
        double regret = 0;
        double[] regretList;
        int advSatisfiedNum = 0;
        System.out.println("-----Result-----");
        BillboardSet[] resultSetList = exp.resultSetList;
        //allResult.add(resultSetList);
        System.out.println("Experiment " + exp.expIndex);

        GreedyFunction greedyFunction = new GreedyFunction();
        greedyFunction.setGamma(exp.gamma);
        regretList = greedyFunction.getTotalRegretDetail(exp.advertierList, resultSetList);
        for (double reg : regretList) {
            regret += reg;
        }

        for (int i = 0; i < exp.advNum; i++) {
            double inf = exp.resultSetList[i].getInfluence(true);
            Advertiser adv = exp.advertierList.get(i);
            if (inf >= adv.getRequiredInf()) {
                totalRevenue += exp.advertierList.get(i).getBudget();
                advSatisfiedNum++;
            }
            if (Setting.test2) {
                if (resultSetList[i].getInfluence(true) >= adv.getRequiredInf()) {
                    System.out.println("Advertiser ID " + resultSetList[i].getAdvID());
                    System.out.println("Required Inf " + adv.getRequiredInf() + "/ Total Inf " + inf);
                    System.out.println(resultSetList[i].getResultString());
                    //System.out.println("Revenue " + revenue);
                    System.out.println("Regret " + greedyFunction.getRegret(adv, exp.resultSetList[i]));
                    System.out.println("-----------------------------------------------");
                    //regret += resultSetList[i].getInfluence(false) - exp.advertierList.get(i).getRequiredInf();
                }
            }
        }

        int index = revenueList.size() - 1;
        if (index != exp.expIndex) {
            this.revenueList.add(new ArrayList<>());
            this.billboardNumList.add(new ArrayList<>());
            this.totalCostList.add(new ArrayList<>());
            this.regretList.add(new ArrayList<>());
            this.satisfiedNumberList.add(new ArrayList<>());
            index++;
        }
        this.revenueList.get(index).add(totalRevenue);
        //System.out.println("Total Revenue : " + totalRevenue);
        System.out.println("Total Regret : " + regret);

        this.billboardNumList.get(index).add(getBillboardNum(exp));
        this.totalCostList.get(index).add(Math.ceil(getTotalCost(exp)));
        this.regretList.get(index).add(regretList);
        this.satisfiedNumberList.get(index).add(advSatisfiedNum);
        System.out.println("----------------");
    }

    /**
     * Generate setting for each advertiser
     *
     * @param exp the experiment setting
     */
    private void loadSetting(ExpSetting exp) {
        Setting.greedyTimer = true;
        Setting.setAdvNum(exp.advNum);
        Setting.setInfModel(exp.model);
        Setting.setMultiBillboard(exp.multiBillboard);
        Setting.setTrajPer(exp.trajPer);
        Setting.beta = exp.beta;
        Setting.test = exp.test;
        Setting.gamma = exp.gamma;

        if (exp.infProb != 0)
            Setting.infProb = exp.infProb;

        if (exp.test && exp.billboardNumber > 0) {
            Setting.setBillboardNumber(exp.billboardNumber);
        }
    }

    private void generateAdv(ExpSetting exp, double totalInf) {
        double budget;
        double requiredInf;
        double totalBudget = 0;
        double avgRequiredInf;
        double adjust = 0.2;
        boolean pass = false;
        Random random = new Random();

        if (advertiserList == null)
            advertiserList = new ArrayList<>();

        exp.advNum = (int) (100 / exp.startPayment);
        avgRequiredInf = (int) ((totalInf * exp.infPercentage) / exp.advNum);
        System.out.println("Adv number " + exp.advNum + " | Adv avg required inf " + avgRequiredInf);
        Setting.setAdvNum(exp.advNum);

        while (!pass) {
            double totalRequiredInf = 0;
            advertiserList = new ArrayList<>();
            exp.advertierList = new ArrayList<>();
            for (int advId = 0; advId < exp.advNum; advId++) {
                //requiredInf = avgRequiredInf * (1.0 - adjust + adjustSetp * (advId + 1));
                requiredInf = avgRequiredInf;
                if (exp.randomBudget)
                    requiredInf *= (1 - adjust) + (adjust * 2.0 * random.nextDouble());
                //requiredInf *= 2.0 * adjust * (1 - random.nextDouble());
                requiredInf = Math.floor(requiredInf);
                budget = requiredInf;
                if (exp.randomBudget)
                    budget *= (1 - adjust / 2) + (adjust * random.nextDouble());
                budget = Math.floor(budget);
                totalBudget += budget;
                Advertiser advertiser = new Advertiser(advId, requiredInf, budget);
                exp.advertierList.add(advertiser);
                advertiserList.add(advertiser);

                totalRequiredInf += requiredInf;

                if (Setting.test2) {
                    System.out.println("Adv-" + advId + " , Inf " + requiredInf + " budget " + budget + " bud/Inf " + budget / requiredInf);
                }

            }
            if ((totalRequiredInf / (totalInf * exp.infPercentage)) > 0.95 &&
                    (totalRequiredInf / (totalInf * exp.infPercentage)) < 1.05)
                pass = true;
        }
        exp.totalPayment = totalBudget;
        System.out.println("Total payment " + totalBudget);

        Collections.shuffle(exp.advertierList);
    }

    private double getTotalCost(ExpSetting exp) {
        double totalCost = 0;
        for (int advID = 0; advID < exp.advNum; advID++) {
            totalCost += exp.resultSetList[advID].getCost();
        }
        return totalCost;
    }

    private int getBillboardNum(ExpSetting exp) {
        int billboardNum = 0;
        for (int advID = 0; advID < exp.advNum; advID++) {
            billboardNum += exp.resultSetList[advID].getBillboardNumber();
        }
        return billboardNum;
    }
}




