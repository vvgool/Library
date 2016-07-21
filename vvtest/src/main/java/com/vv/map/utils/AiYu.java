package com.vv.map.utils;

/**
 * Created by wiesen on 16-7-12.
 */
public class AiYu {

    /**
     * she is the only one and is very important for me
     * I must have to  cherished her;
     * @return
     */
    public static AiYu getInstance(){
        return InstanceHolder.mAiYu;
    }

    private static class InstanceHolder{
        private static final AiYu mAiYu = new AiYu();
    }

    public void doWhat(){

    }

    public void toManagerMoney(){

    }

    public void toDoShoping(){

    }

    public void toEat(){

    }

}
