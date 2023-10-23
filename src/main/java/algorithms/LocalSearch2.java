package algorithms;

import entity.*;

import java.util.ArrayList;
import java.util.List;

//advertiser-driven local search
public class LocalSearch2 {

    private int advNum; // how many advertisers
    private double optRegret;
    public boolean isUpdate = true;

    private ExpSetting expSetting;
    private ArrayList<Advertiser> advertiserList;
    private BillboardSet[] resultSetList; //final result for all advertisers
    private List<Billboard> remainingBoards; // all remaining billboards
    private ArrayList<Double> requiredInfList; // the required influence for each advertiser

    private GreedyFunction greedyFunction;

    public LocalSearch2(List<Billboard> billboards, BillboardSet[] resultSetList, ExpSetting exp) {
        advNum = exp.advertierList.size();
        this.expSetting = exp;
        this.remainingBoards = billboards;
        this.requiredInfList = new ArrayList<>();
        this.resultSetList = resultSetList;
        this.advertiserList = exp.advertierList;

        greedyFunction = new GreedyFunction();

        for (int i = 0; i < advNum; i++) {
            requiredInfList.add(exp.advertierList.get(i).getRequiredInf());
        }
    }

    public void getResult() {
        search();
    }

    private void search() {
        boolean update = false;
        int iteration = 0;
        int updataTime;
        int adv1, adv2;
        double optRegret = 0, regret1, regret2, newRegret, currentRegret;
        boolean[] satisfyList = new boolean[advNum];
        for (int i = 0; i < advNum; i++) {
            if (requiredInfList.get(i) <= resultSetList[i].getInfluence(false)) {
                satisfyList[i] = true;
            } else {
                satisfyList[i] = false;
                update = true;
            }
        }
        //there is no advertiser can be exchanged
        if (!update) {
            isUpdate = false;
            return;
        }
        do {
            iteration++;
            updataTime = 0;
            update = false;
            for (int i = 0; i < advNum - 1; i++) {
                adv1 = adv2 = 0;
                regret1 = greedyFunction.getRegret(advertiserList.get(i), resultSetList[i]);
                for (int n = i + 1; n < advNum; n++) {
                    regret2 = greedyFunction.getRegret(advertiserList.get(n), resultSetList[n]);
                    currentRegret = regret1 + regret2;
                    newRegret = greedyFunction.getRegret(advertiserList.get(i), resultSetList[n]) + greedyFunction.getRegret(advertiserList.get(n), resultSetList[i]);
                    if (newRegret < currentRegret) {
                        if (optRegret < (currentRegret - newRegret)) {
                            update = true;
                            optRegret = currentRegret - newRegret;
                            adv1 = i;
                            adv2 = n;
                        }
                    }

                }
                if (adv1 != adv2) {
                    ArrayList<Billboard> billboardList1 = resultSetList[adv1].getAllBillboards();
                    ArrayList<Billboard> billboardList2 = resultSetList[adv2].getAllBillboards();
                    resultSetList[adv1].release();
                    resultSetList[adv2].release();
                    resultSetList[adv1].add(billboardList2);
                    resultSetList[adv2].add(billboardList1);
                }
            }


//                if (satisfyList[i]) {
//
//
//                    double regretGain = getRegret(resultSetList[i], advertiserList.get(i));
//                    regretGain = requiredInfList.get(i) - regretGain;
//                    //resultSetList[i].jump = true;
//
//                    ExpSetting exp = new ExpSetting();
//                    exp.advertierList = new ArrayList<>();
//                    for (int n = 0; n < advNum; n++) {
//                        if (!satisfyList[n]) {
//                            resultSetList[n].release();
//                            exp.advertierList.add(expSetting.advertierList.get(n));
//                        }
//                    }
//
//                    greedyFunction.orderByCostPerRough(exp.advertierList);
//                    //checkDoubleSelect(resultSetList);
//
//                    ArrayList<Billboard> billboards = resultSetList[i].getAllBillboards();
//                    resultSetList[i].release();
//                    remainingBoards.addAll(billboards);
//
//                    GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(remainingBoards, exp);
//                    greedyAlgorithm.greedyForOne();
//                    BillboardSet[] results = greedyAlgorithm.getResultSetList();
//                    double regretLoss = getRegret2(results, exp.advertierList);
//
//                    if (regretGain < regretLoss) {
//                        //System.out.println("local search2 " + regretGain + "->" + regretLoss);
//                        updataTime++;
//                        for (BillboardSet result : results) {
//                            for (int m = 0; m < advNum; m++) {
//                                if (result.getAllBillboards().size() > 0 && result.getAdvID() == resultSetList[m].getAdvID()) {
//                                    resultSetList[m].add(result.getAllBillboards());
//                                    satisfyList[m] = true;
//                                    break;
//                                }
//                            }
//                        }
//                        satisfyList[i] = false;
//                        update = true;
//                    } else {
//                        for (BillboardSet billboardSet : results) {
//                            remainingBoards.addAll(billboardSet.getAllBillboards());
//                            billboardSet.release();
//                        }
//                        for (Billboard billboard : billboards) {
//                            resultSetList[i].add(billboard);
//                            remainingBoards.remove(billboard);
//                        }
//                        //checkDoubleSelect(resultSetList);
//                    }
//                }
//            }
//            double regret = greedyFunction.getTotalRegret(this.advertiserList, this.resultSetList);
//            if (optRegret > regret || optRegret == 0)
//                optRegret = regret;
//            else
//                update = false;
            //System.out.println("LS2 " + regret);
        } while (update);
    }

    //the amount of decreasing regret
    private double getRegret2(BillboardSet[] results, ArrayList<Advertiser> advertiserList) {
        double totalRegret = 0;
        double regret;
        for (int i = 0; i < results.length; i++) {
            double payment = advertiserList.get(i).getBudget();
            regret = getRegret(results[i], advertiserList.get(i));
            if (payment != regret) {
                regret = payment - regret;
                totalRegret += regret;
            }
        }
        return totalRegret;
    }

    public double getRegret(BillboardSet resultSetList, Advertiser advertiser) {
        double inf = resultSetList.getInfluence(false);
        if (inf < advertiser.getRequiredInf()) {
            remainingBoards.addAll(resultSetList.getAllBillboards());
            resultSetList.release();
            return advertiser.getBudget();
        } else {
            return advertiser.getBudget() * (inf - advertiser.getRequiredInf()) / advertiser.getRequiredInf() + resultSetList.getCost();
        }
    }


    public double getOptRegret() {
        return optRegret;
    }

    public void setOptRegret(double optRegret) {
        this.optRegret = optRegret;
    }

    private void checkDoubleSelect(BillboardSet[] billboardSets) {
        int num = 0;
        ArrayList<Billboard> billboards = new ArrayList<>();
        for (BillboardSet billboardSet : billboardSets) {
            for (Billboard selBillboard : billboardSet.getAllBillboards()) {
                for (Billboard billboard : billboards) {
                    if (selBillboard.panelID.equals(billboard.panelID)) {
                        System.out.println("LS2 - Billboard " + selBillboard.panelID + " has been double selected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        num++;
                        break;
                    }
                }
                billboards.add(selBillboard);
            }
        }
        if (num > 0) {
            System.out.println("Check if there is any billboard that has been double selected");
            System.out.println("Repeat number " + num);
        }
    }
}
