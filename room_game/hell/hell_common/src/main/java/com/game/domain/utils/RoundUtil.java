package com.game.domain.utils;

import com.alibaba.fastjson.JSONObject;
import com.game.domain.enums.CommonValue;
import com.game.domain.pojo.ResultData;
import com.game.domain.pojo.Role;
import com.game.domain.pojo.RoundData;
import com.game.domain.pojo.Skill;
import com.game.domain.pojo.client.CRound;
import com.game.domain.pojo.server.ActionData;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/27 15:39
 * @Since 0.0.1
 */
public class RoundUtil {
    static Random random = new Random();

    static List<Integer> actions = new ArrayList<Integer>(){
        {
            this.add(1);
            this.add(2);
            this.add(3);
            this.add(4);
        }
    };

    static List<Integer> skills = new ArrayList<Integer>(){
        {
            this.add(1);
            this.add(2);
            this.add(3);
        }
    };

    /**
     * 补齐缺失的回合数据
     *
     * @param roundData1
     * @param roundData2
     */
    public static void addRoundData(Role r1,RoundData roundData1, Role r2,RoundData roundData2) {
        long l = System.currentTimeMillis();
        if (!Optional.ofNullable(roundData1.left).isPresent()) {
            roundData1.left = randomCRound(r1,1);
            roundData1.leftTime = l;
        }
        if (!Optional.ofNullable(roundData1.right).isPresent()) {
            roundData1.right = randomCRound(r1,2);
            roundData1.rightTime = l;
        }
        if (!Optional.ofNullable(roundData2.left).isPresent()) {
            roundData2.left = randomCRound(r2,1);
            roundData2.leftTime = l;
        }
        if (!Optional.ofNullable(roundData2.right).isPresent()) {
            roundData2.right = randomCRound(r2,2);
            roundData2.rightTime = l;
        }
    }

    public static CRound randomCRound(Role r,int type) {
        CRound cRound = new CRound();
        int s = r.getMp()>=200 ? 4:3; // 蓝量大于200可以发技能
        int i = (int) (s*Math.random());

        cRound.setAction(actions.get(i));

        if(cRound.getAction()==4){
            cRound.setSkillId(skills.get((int) (skills.size()*Math.random())));
            r.setMp(r.getMp()-200);
        }

        cRound.setType((byte) type);
        return cRound;
    }

    /**
     * 对两个操作进行判断
     *
     * @param roundData1
     * @param roundData2
     * @return
     */
    public static ResultData resultChange(Role r1, RoundData roundData1, Skill skills1, Role r2, RoundData roundData2, Skill skills2, long roundTime) {
        ResultData resultData = new ResultData();
            // 不出技能
            // 以p1为视角
            ActionData p1Left = createActionData(r1, roundTime, roundData1.left, roundData1.leftTime, roundData2.right);
            ActionData p2Right = createActionData(r2, roundTime, roundData2.right, roundData2.rightTime, roundData1.left);


            resultChange(r1, p1Left, roundData1.left,skills1, r2, p2Right, roundData2.right,skills2);

            r1.setHp(r1.getHp()+p1Left.changeHp+p1Left.action2ChangeHp);
            r1.setMp(r1.getMp()+p1Left.changeMp+p1Left.action2ChangeMp);

            r2.setHp(r2.getHp()+p2Right.changeHp+p2Right.action2ChangeHp);
            r2.setMp(r2.getMp()+p2Right.changeMp+p2Right.action2ChangeMp);

            ActionData p1Right = createActionData(r1, roundTime, roundData1.right, roundData1.rightTime, roundData2.left);
            ActionData p2Left = createActionData(r2, roundTime, roundData2.left, roundData2.leftTime, roundData1.right);

            resultChange(r1, p1Right, roundData1.right,skills1, r2, p2Left, roundData2.left,skills2);

            r1.setHp(r1.getHp()+p1Right.changeHp+p1Right.action2ChangeHp);
            r1.setMp(r1.getMp()+p1Right.changeMp+p1Right.action2ChangeMp);

            r2.setHp(r2.getHp()+p2Left.changeHp+p2Left.action2ChangeHp);
            r2.setMp(r2.getMp()+p2Left.changeMp+p2Left.action2ChangeMp);

            // 双方出招完毕 计算结果
            resultData.p1Left = p1Left;
            resultData.p2Right = p2Right;
            resultData.p1Right = p1Right;
            resultData.p2Left = p2Left;

            System.out.println("plLeft:"+JSONObject.toJSONString(p1Left));
            System.out.println("p2Right:"+JSONObject.toJSONString(p2Right));

            System.out.println("p1Right:"+JSONObject.toJSONString(p1Right));
            System.out.println("p2Left:"+JSONObject.toJSONString(p2Left));

        return resultData;
    }

    /**
     * 攻击判断
     * action 0未操作，1打2防3畜力4技能   其中0应该不会出现（上级方法补全数据）
     *
     * @param r1
     * @param a1
     * @param c1
     * @param r2
     * @param a2
     * @param c2
     */
    public static void resultChange(Role r1, ActionData a1, CRound c1,Skill skill1, Role r2, ActionData a2, CRound c2,Skill skill2) {


        if (c1.getAction() == 1) {
            // p1是攻击
            switch (c2.getAction()) {
                case 1:// p2攻击
                    // 比较出拳时间 先出拳的对对方造成伤害
                    // 同为出拳，闪避为0
                    // 攻击出拳/蓄力方造成伤害增加20怒气
                    // 正常受击：受击方减少1格血量，受击方增加10怒气。

                    if(skill1.getInitTime()>skill2.getInitTime()){
                        hitHit(r1,a1,skill1,r2,a2,skill2);
                    }else if (skill1.getInitTime()<skill2.getInitTime()){
                        hitHit(r2,a2,skill2,r1,a1,skill1);
                    }else {
                        if (a1.time <= a2.time) {
                            hitHit(r1,a1,skill1,r2,a2,skill2);
                        } else {
                            hitHit(r2,a2,skill2,r1,a1,skill1);
                        }
                    }
                    break;
                case 2:
                    hitBlock(r1, a1,skill1, r2, a2,skill2);
                    break;
                case 3:
                    hitXuli(r1, a1,skill1, r2, a2,skill2);
                    break;
                default:
                    break;
            }
        } else if (c1.getAction() == 2) {
            // p1 是格挡

            switch (c2.getAction()) {
                case 1:// p2攻击
                    hitBlock(r2, a2,skill2, r1, a1,skill1);
                    // 需要交换p1 p2 因为攻击格挡双方交换了
                    break;
                case 2:
                    // p2 格挡
                    // 双方格挡 无回合数据

                    a1.hp -= 0;
                    a1.changeHp -= 0;
                    a1.mp += 0;
                    a1.changeMp += 0;

                    a2.hp -= 0;
                    a2.changeHp -= 0;
                    a2.mp += 0;
                    a2.changeMp += 0;

                    a1.action2 = CommonValue.ACTION2_NOMAIL; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
                    a1.action2ChangeHp -= 0;
                    a1.action2ChangeMp += 0;

                    a2.action2 = CommonValue.ACTION2_NOMAIL;
                    a2.action2ChangeHp -= 0;
                    a2.action2ChangeMp += 0;

                    break;
                case 3:
                    // 蓄力
                    // 造成伤害：攻击出拳/蓄力方造成伤害增加20怒气。攻击格挡造成伤害增加10怒气。
                    a1.hp -= 0;
                    a1.changeHp -= 0;
                    a1.mp += 0;
                    a1.changeMp += 0;

                    a2.hp -= 0;
                    a2.changeHp -= 0;
                    a2.mp += CommonValue.xuliMpAdd;
                    a2.changeMp += CommonValue.xuliMpAdd;

                    a1.action2 = CommonValue.ACTION2_NOMAIL; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
                    a1.action2ChangeHp -= 0;
                    a1.action2ChangeMp += 0;

                    a2.action2 = CommonValue.ACTION2_NOMAIL;
                    a2.action2ChangeHp -= 0;
                    a2.action2ChangeMp += 0;

                    break;
                default:
                    break;
            }
        } else if (c1.getAction() == 3) {
            // p2 是蓄力

            switch (c2.getAction()) {
                case 1:// p2攻击
                    hitXuli(r2, a2,skill2, r1, a1,skill1);
                    // 需要交换p1 p2 因为攻击蓄力双方交换了
                    break;
                case 2:
                    // p2 格挡
                    // 造成伤害：攻击出拳/蓄力方造成伤害增加20怒气。攻击格挡造成伤害增加10怒气。
                    a1.hp -= 0;
                    a1.changeHp -= 0;
                    a1.mp += CommonValue.xuliMpAdd;
                    a1.changeMp += CommonValue.xuliMpAdd;

                    a2.hp -= 0;
                    a2.changeHp -= 0;
                    a2.mp += 0;
                    a2.changeMp += 0;


                    a1.action2 = CommonValue.ACTION2_NOMAIL; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
                    a1.action2ChangeHp -= 0;
                    a1.action2ChangeMp += 0;

                    a2.action2 = CommonValue.ACTION2_NOMAIL;
                    a2.action2ChangeHp -= 0;
                    a2.action2ChangeMp += 0;


                    break;
                case 3:
                    // 蓄力
                    // 造成伤害：攻击出拳/蓄力方造成伤害增加20怒气。攻击格挡造成伤害增加10怒气。
                    a1.hp -= 0;
                    a1.changeHp -= 0;
                    a1.mp += CommonValue.xuliMpAdd;
                    a1.changeMp += CommonValue.xuliMpAdd;

                    a2.hp -= 0;
                    a2.changeHp -= 0;
                    a2.mp += CommonValue.xuliMpAdd;
                    a2.changeMp += CommonValue.xuliMpAdd;

                    a1.action2 = CommonValue.ACTION2_NOMAIL; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
                    a1.action2ChangeHp -= 0;
                    a1.action2ChangeMp += 0;

                    a2.action2 = CommonValue.ACTION2_NOMAIL;
                    a2.action2ChangeHp -= 0;
                    a2.action2ChangeMp += 0;


                    break;
                default:
                    break;
            }
        }
    }

    public static void hitHit(Role hit, ActionData a1,Skill skill1, Role beHit, ActionData a2,Skill skill2){
        a1.hp -= CommonValue.hitSuccHpDown;
        a1.changeHp -= CommonValue.hitSuccHpDown;
        a1.mp += CommonValue.hitSuccMpAdd;
        a1.changeMp += CommonValue.hitSuccMpAdd;

        a2.hp -= CommonValue.beHitHpDown * skill1.getHitAdd() * skill2.getBeHitAdd();
        a2.changeHp -= CommonValue.beHitHpDown * skill1.getHitAdd() * skill2.getBeHitAdd();
        a2.mp += CommonValue.beHitMpAdd ;
        a2.changeMp += CommonValue.beHitMpAdd;

        a1.action2 = CommonValue.ACTION2_BEHIT; // 0不触发，1格挡，2闪避 3格挡反击 4挨打 5攻击
        a1.action2ChangeHp -= 0;
        a1.action2ChangeMp += 0;

        a2.action2 = CommonValue.ACTION2_HIT;
        a2.action2ChangeHp -= 0;
        a2.action2ChangeMp += 0;
    }

    /**
     * 攻击蓄力处理
     * 返回结果中的p1为攻击方法（hit） p2为蓄力方（xuli）
     *
     * @param hit
     * @param xuli
     * @return
     */
    public static void hitXuli(Role hit, ActionData a1,Skill skill1, Role xuli, ActionData a2,Skill skill2) {
        // 蓄力

        boolean hitSucc = true;

        if(isSucc((int)(skill1.getIgnoreDodge()*100), CommonValue.RATIO_100)){
            // 判断必中
            hitSucc = true;
        }else  if (isSucc(xuli.getMiss()+10, CommonValue.RATIO_100)){
            hitSucc = false;
        }
        if (!hitSucc) {
            // 触发闪避 闪避加成10
            // 闪避成功，则伤害作废

            a1.action2 = CommonValue.ACTION2_MISS; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
            a1.action2ChangeHp -= 0;
            a1.action2ChangeMp += 0;

            a2.action2 = CommonValue.ACTION2_HIT;
            a2.action2ChangeHp -= 0;
            a2.action2ChangeMp += 0;
        } else {
            hitSuc(hit,a1,skill1,xuli,a2,skill2);

            // 造成伤害：攻击出拳/蓄力方造成伤害增加20怒气。攻击格挡造成伤害增加10怒气。
            /*a1.hp -= CommonValue.hitSuccHpDown;
            a1.changeHp -= CommonValue.hitSuccHpDown;
            a1.mp += CommonValue.hitSuccMpAdd;
            a1.changeMp += CommonValue.hitSuccMpAdd;

            a2.mp += CommonValue.xuliMpAdd;
            a2.changeMp += CommonValue.xuliMpAdd;

            // 暴击概率 = 攻击方暴击率 - 防守方韧性
            if (isSucc((hit.getCs() - xuli.getAntiCrit()), CommonValue.RATIO_100)) {
                a1.isCrit = 1;

                // rd.p2HpDown+=CommonValue.beHitHpDown*(r1.getCd());
                a2.hp -= getCd(CommonValue.beHitHpDown, hit.getCd()) * skill1.getHitAdd() * skill2.getBeHitAdd();
                a2.changeHp -= getCd(CommonValue.beHitHpDown, hit.getCd()) * skill1.getHitAdd() * skill2.getBeHitAdd();
            } else {
                a2.hp -= CommonValue.beHitHpDown * skill1.getHitAdd() * skill2.getBeHitAdd();
                a2.changeHp -= CommonValue.beHitHpDown * skill1.getHitAdd() * skill2.getBeHitAdd();
            }

            a1.action2 = CommonValue.ACTION2_BEHIT; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
            a1.action2ChangeHp -= 0;
            a1.action2ChangeMp += 0;

            a2.action2 = CommonValue.ACTION2_HIT;
            a2.action2ChangeHp -= 0;
            a2.action2ChangeMp += 0;*/


        }


    }

    /**
     * 攻击格挡处理
     * 返回结果中的p1为攻击方法（hit） p2为被攻击方（block）
     *
     * @param hit
     * @param block
     * @return
     */
    public static void hitBlock(Role hit, ActionData a1,Skill skill1, Role block, ActionData a2,Skill skill2) {
        // 格挡
        // 我方出拳，对方格挡，优先判定是否闪避 同为出拳，闪避为0，闪避仅在玩家蓄力状态下出现
        // 判断闪避
        /*if (isSucc(block.getMiss(), CommonValue.RATIO_100)) {
            // 闪避成功，则伤害作废

            a1.action2 = CommonValue.ACTION2_MISS; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
            a1.action2ChangeHp -= 0;
            a1.action2ChangeMp += 0;

            a2.action2 = CommonValue.ACTION2_HIT;
            a2.action2ChangeHp -= 0;
            a2.action2ChangeMp += 0;

        } else {*/
            // 闪避失败，继续判定是否格挡
            if (isSucc(block.getBlock(), CommonValue.RATIO_100)) {
                // 攻击格挡造成伤害增加10怒气
                a1.hp -= CommonValue.hitSuccHpDown;
                a1.changeHp -= CommonValue.hitSuccHpDown;
                a1.mp += CommonValue.hitBlockMpAdd;
                a1.changeMp += CommonValue.hitBlockMpAdd;
                // 成功格挡：减少0.5格血量，增加5怒气。有概率触发格挡反击。
                a2.hp -= CommonValue.blockHpDown;
                a2.changeHp -= CommonValue.blockHpDown;
                a2.mp += CommonValue.blockMpAdd;
                a2.changeMp += CommonValue.blockMpAdd;

                a1.action2 = CommonValue.ACTION2_BLOCK; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
                a1.action2ChangeHp -= 0;
                a1.action2ChangeMp += 0;

                a2.action2 = CommonValue.ACTION2_HIT;
                a2.action2ChangeHp -= 0;
                a2.action2ChangeMp += 0;

                // 若格挡成功，判定是否触发格挡反击（追加一次格挡手的出拳攻击，该攻击必中）
                if (isSucc(block.getBlockHit(), CommonValue.RATIO_100)) {
                    a1.action2 = CommonValue.ACTION2_BLOCKATT; // 0不触发，1格挡，2闪避 3格挡反击 4挨打

                    a1.hp -= CommonValue.beHitHpDown;
                    a1.action2ChangeHp -= CommonValue.beHitHpDown;
                    a1.mp += CommonValue.beHitMpAdd;// 格挡被攻击是否要加怒气
                    a1.action2ChangeMp += CommonValue.beHitMpAdd;// 格挡被攻击是否要加怒气

                }

            } else {
                hitSuc(hit,a1,skill1,block,a2,skill2);

            }
        /*}*/
    }

    public static  void hitSuc(Role hit, ActionData a1,Skill skill1, Role beHit, ActionData a2,Skill skill2){
        a1.hp -= CommonValue.hitSuccHpDown;
        a1.changeHp -= CommonValue.hitSuccHpDown;
        a1.mp += CommonValue.hitSuccMpAdd;
        a1.changeMp += CommonValue.hitSuccMpAdd;
        // 若格挡失败，则判定攻击是否触发暴击，视为我方造成伤害，对手正常受击
        // 正常受击：受击方减少1格血量，受击方增加10怒气。

        a2.mp += CommonValue.beHitMpAdd;
        a2.changeMp += CommonValue.beHitMpAdd;

        a1.action2 = CommonValue.ACTION2_BEHIT; // 0不触发，1格挡，2闪避 3格挡反击 4挨打
        a1.action2ChangeHp -= 0;
        a1.action2ChangeMp += 0;

        a2.action2 = CommonValue.ACTION2_HIT;
        a2.action2ChangeHp -= 0;
        a2.action2ChangeMp += 0;

        // 暴击概率 = 攻击方暴击率 - 防守方韧性
        if (isSucc((hit.getCs() - beHit.getAntiCrit()), CommonValue.RATIO_100)) {
            a1.isCrit = 1;

            // rd.p2HpDown+=CommonValue.beHitHpDown*(r1.getCd());
            a2.hp -= getCd(CommonValue.beHitHpDown, hit.getCd());
            a2.changeHp -= getCd(CommonValue.beHitHpDown, hit.getCd());
        } else {
            a2.hp -= CommonValue.beHitHpDown;
            a2.changeHp -= CommonValue.beHitHpDown;
        }
    }

    public static ActionData createActionData(Role r, long roundTime, CRound c1, long t, CRound c2) {
        ActionData data = new ActionData();
        data.action = (byte) c1.getAction();
        data.action2 = (byte) c2.getAction();

        data.hp = r.getHp();
        data.mp = r.getMp();
        data.skillId = (byte) c1.getSkillId();
        data.time = (int) (t - roundTime);

        return data;
    }


    public static int getCd(int initHit, int cd) {
        BigDecimal b = new BigDecimal((double) cd / CommonValue.RATIO_100);
        b.multiply(new BigDecimal(initHit));
        return b.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }


    // 概率判断 这边只有成功失败
    public static boolean isSucc(int prob, int ratio) {

        if (prob >= 1.0*ratio) return true;
        if (prob <= 0) return false;
        double r = getRandom() * ratio;
        if (r <= prob) return true;
        return false;
    }

    public static double getRandom() {
        double r = random.nextDouble();
        if (r == 0.0d) {
            r = getRandom();
        }
        return r;
    }

    public static Role getRole(int roleId) {
        Role role = CommonValue.roleMap.get(roleId);
        Role newRole = new Role();
        BeanUtils.copyProperties(role, newRole);
        return newRole;
    }
}
