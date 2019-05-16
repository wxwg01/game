package com.game.service.room;

import com.game.domain.enums.CommonValue;
import com.game.domain.pojo.ResultData;
import com.game.domain.pojo.Role;
import com.game.domain.pojo.RoundData;
import com.game.domain.pojo.Skill;
import com.game.domain.pojo.client.CRound;
import com.game.domain.pojo.server.ActionData;
import com.game.domain.pojo.server.PlayerData;
import com.game.domain.pojo.server.SRoundResult;
import com.game.domain.utils.RoundUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <一句话简单说明类功能>
 * </br>
 *
 * @Author wanggang
 * @Date 2018/9/12 15:12
 * @Since 0.0.1
 */
public class RoundRunnable implements Runnable {
    private int roomId;
    private byte roomType;

    private PlayerData p1;
    private PlayerData p2;

    private Role r1;
    private Role r2;

    List<Skill> skills1 = new ArrayList<>();
    List<Skill> skills2 = new ArrayList<>();

    private short num = 0;// 回合数

    List<RoundData> roundDataListP1;
    List<RoundData> roundDataListP2;

    long createTime;//创建时间
    long preRoundTime;//上回合时间

    public RoundRunnable(int roundSize, byte roomType) {
        this.roundDataListP1 = new ArrayList<>(roundSize);
        this.roundDataListP2 = new ArrayList<>(roundSize);
        for (int i = 0; i < roundSize; i++) {
            roundDataListP1.add(new RoundData());
            roundDataListP2.add(new RoundData());
        }

        this.roomType = roomType;
    }


    public PlayerData getP1() {
        return p1;
    }

    public void setP1(PlayerData p1) {
        this.p1 = p1;
    }

    public PlayerData getP2() {
        return p2;
    }

    public void setP2(PlayerData p2) {
        this.p2 = p2;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<RoundData> getRoundDataListP1() {
        return roundDataListP1;
    }

    public void setRoundDataListP1(List<RoundData> roundDataListP1) {
        this.roundDataListP1 = roundDataListP1;
    }

    public List<RoundData> getRoundDataListP2() {
        return roundDataListP2;
    }

    public void setRoundDataListP2(List<RoundData> roundDataListP2) {
        this.roundDataListP2 = roundDataListP2;
    }

    public byte getRoomType() {
        return roomType;
    }

    public void setRoomType(byte roomType) {
        this.roomType = roomType;
    }

    public Role getR1() {
        return r1;
    }

    public void setR1(Role r1) {
        this.r1 = r1;
    }

    public Role getR2() {
        return r2;
    }

    public void setR2(Role r2) {
        this.r2 = r2;
    }

    public long getPreRoundTime() {
        return preRoundTime;
    }

    public void setPreRoundTime(long preRoundTime) {
        this.preRoundTime = preRoundTime;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    @Override
    public void run() {

        if(!Optional.ofNullable(skills1).isPresent()) skills1 = new ArrayList<>();
        if(!Optional.ofNullable(skills2).isPresent()) skills2 = new ArrayList<>();

        while (true) {
            // 掉线了一个
            if (!Optional.ofNullable(p1).isPresent()) {
                //触发事件
                break;
            }
            if (!Optional.ofNullable(p2).isPresent()) {
                //触发事件
                break;
            }
            if (num >= CommonValue.rounds) {
                // 回合结束
                break;
            }

            RoundData roundData1 = getRoundDataListP1().get(num);
            RoundData roundData2 = getRoundDataListP2().get(num);

            boolean isOverTime = (System.currentTimeMillis() - preRoundTime) > CommonValue.roundWait * 1000;
            // 双方出完左右 或者 回合时间结束
            if ((Optional.ofNullable(roundData1.left).isPresent() && Optional.ofNullable(roundData1.right).isPresent() &&
                    Optional.ofNullable(roundData2.left).isPresent() && Optional.ofNullable(roundData2.right).isPresent()) || isOverTime) {

                if (isOverTime) {
                    RoundUtil.addRoundData(r1,roundData1, r2,roundData2);
                }

                // 出了技能
                // 技能看成是对伤害的加成的buf
                Skill skill1 = getSkill(skills1,roundData1);
                Skill skill2 = getSkill(skills2,roundData2);

                // 双方出招完毕
                // 进行计算
                //    public static ResultData resultChange(Role r1,RoundData roundData1,Role r2, RoundData roundData2){
                ResultData rd = RoundUtil.resultChange(r1, roundData1,skill1, r2, roundData2,skill2, preRoundTime);
                preRoundTime = System.currentTimeMillis();
                num++;

                SRoundResult roundResult = new SRoundResult();
                roundResult.setRound(num);
                roundResult.setTimeOut(CommonValue.roundWait);
                List<ActionData> left = new ArrayList<ActionData>() {
                    {
                        this.add(rd.p1Left);
                        this.add(rd.p2Left);
                    }
                };// 左拳
                List<ActionData> right = new ArrayList<ActionData>() {
                    {
                        this.add(rd.p1Right);
                        this.add(rd.p2Right);
                    }
                };// 右拳
                roundResult.setLeft(left);
                roundResult.setRight(right);
               /* System.out.println("left:"+JSONObject.toJSONString(left));
                System.out.println("right:"+JSONObject.toJSONString(right));*/

                System.out.println("---------------------------------------------------");
//                System.out.println(JSONObject.toJSONString(roundResult));

                try {
//                    SessionManager.getInstance().sendMessage(SessionManager.getInstance().getSession(p1.getUuid()), roundResult);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 触发 等级 经验 金钱变化
//                EventUtil.fireEvent(SEnumValue.SROUNDRESULT.getCode(),roundResult);


                // 一个人没血也要触发线程结束


                // 返回当前回合数据
                // 有结果后 清空回合数据
               /* if (!RoomManager.getInstance().cleanRoundData(this)) {
                    EventUtil.fireEvent(SEnumValue.SROUNDEND.getCode(), roundResult);
                }*/
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public Skill getSkill(List<Skill> skills,RoundData roundData){
        getSkill(skills,roundData.left);
        getSkill(skills,roundData.right);

        Skill skill = new Skill();
        if(skills.size()>0){
            skills.forEach(it->{
                it.setRound(it.getRound()-1);

                if(it.getInitTime()<skill.getInitTime()){
                    skill.setInitTime(it.getInitTime());
                }
                skill.setHitAdd(skill.getHitAdd() * it.getHitAdd());

                skill.setBlockHitMpAdd(skill.getBlockHitMpAdd() * it.getBlockHitMpAdd());

                skill.setBeHitAdd(skill.getBeHitAdd() * it.getBeHitAdd());

                skill.setBlockHitAdd(skill.getBlockHitAdd() * it.getBlockHitAdd());

                skill.setIgnoreDodge(skill.getIgnoreDodge() * it.getIgnoreDodge());
            });
            skills.removeIf(it->it.getRound()<=0);
        }
        return skill;

    }


    public void getSkill(List<Skill> skills, CRound cRound){
        if(cRound.getAction()==4 && cRound.getSkillId()>0){
            // 4是技能
            Skill skill = CommonValue.skillMap.get(cRound.getAction());
            if(Optional.ofNullable(skill).isPresent()){
                skills.removeIf(it->it.getId()== skill.getId());
                Skill sk = new Skill();
                BeanUtils.copyProperties(skill,sk);
                skills.add(sk);
            }
        }
    }

}
