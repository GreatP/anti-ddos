package com.cetc.security.ddos.persistence.service;


import com.cetc.security.ddos.persistence.*;
import com.cetc.security.ddos.persistence.AttackDetailEntity;
import com.cetc.security.ddos.persistence.dao.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class AttackDetailService {

    @Autowired
    private AttackDetailDao attackDetailDao;

    @Autowired
    private ProtectObjectDao protectObjectDao;

    @Autowired
    private  AttackIpDao attackIpDao;

    @Autowired
    private UserPoDao userPoDao;


    protected List<AttackDetailEntity> getAttackDetailByUserId(int userId) {
        List<UserPoEntity> userPoEntities = userPoDao.findById(userId);
        List<AttackDetailEntity> ret = new ArrayList<AttackDetailEntity>();
        for (UserPoEntity upe : userPoEntities) {
            List<AttackDetailEntity> attackDetailEntities =
                    attackDetailDao.findByPoId(upe.getProtectObjectEntity().getId());
            if (attackDetailEntities == null) {
                continue;
            }
            ret.addAll(attackDetailEntities);
        }
        return ret;
    }

    /**************************************add by lb*****************************************************/
    public List<AttackTypeCount> getAttackTypeCount () {
        return attackDetailDao.groupByType();
    }

    public List<AttackTypeCount> getAttackTypeCountByUserId(int userId) {
        boolean flag = false;
        List<AttackTypeCount> attackTypeCounts = new ArrayList<AttackTypeCount>();

        List<AttackDetailEntity> attackDetailEntities = getAttackDetailByUserId(userId);
        for (AttackDetailEntity ade : attackDetailEntities) {
               for (AttackTypeCount atc : attackTypeCounts) {
                if (ade.getAttackType() == atc.getAttack_type()) {
                    atc.setCount(atc.getCount() + 1);
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                AttackTypeCount tmpAtc = new AttackTypeCount();
                tmpAtc.setCount(1);
                tmpAtc.setAttack_type(ade.getAttackType());
                attackTypeCounts.add(tmpAtc);
            }
        }

        return attackTypeCounts;
    }

    public List<AttackSrcTop10> getAttackSrcTop10 () {
        List<AttackSrcTop10> srcTop10 = new ArrayList<AttackSrcTop10>();
        List<Object[]> list = attackDetailDao.findByIpPeakTop10();
        int cnt = 0;
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Object[] objects = (Object[])iterator.next();
                AttackSrcTop10 attackSrcTop10 = new AttackSrcTop10();
                attackSrcTop10.setIp((String) objects[0]);
                attackSrcTop10.setNewPeak((Long) objects[1]);
                srcTop10.add(attackSrcTop10);
                cnt++;
                if (cnt >= 10) {
                    break;
                }
            }
        }

        return srcTop10;
    }

    public AttackTotalStat getAttackTotalStat() {
        Long result = null;
        AttackTotalStat attackTotalStat = new AttackTotalStat();
        long count = attackDetailDao.countAll();
        attackTotalStat.setAttackCount(count);
        attackTotalStat.setDefenseCount(count);
        result = attackDetailDao.sumByProperty("totalBytes");
        if (result == null) {
            attackTotalStat.setCleanTraffic(0);
        } else {
            attackTotalStat.setCleanTraffic(result.longValue());
        }

        attackTotalStat.setAttackSrcNum(attackIpDao.countAll());

        return attackTotalStat;
    }

    public AttackTotalStat getAttackTotalStatByUserId(int userId) {
        AttackTotalStat attackTotalStat = new AttackTotalStat();
        Map<String, String> map = new HashMap<String,String>();
        long count = 0, totalBytes = 0, attackSrcNum = 0;

        List<AttackDetailEntity> attackDetailEntities = getAttackDetailByUserId(userId);
        for (AttackDetailEntity attackDetailEntity : attackDetailEntities) {
            count++;
            totalBytes += attackDetailEntity.getTotalBytes();

            /* 检查是否有重复攻击源，如果有，就不再统计 */
            if (map.get(attackDetailEntity.getAttackip()) == null) {
                map.put(attackDetailEntity.getAttackip(), attackDetailEntity.getAttackip());
                attackSrcNum++;
            }
        }

        attackTotalStat.setAttackCount(count);
        attackTotalStat.setDefenseCount(count);
        attackTotalStat.setCleanTraffic(totalBytes);
        attackTotalStat.setAttackSrcNum(attackSrcNum);

        return attackTotalStat;
    }

    public List<AttackDetailEntity> getByStatus(int status) {
        return attackDetailDao.findByStatus(status);
    }
    public List<AttackDetailEntity> get(String attackip,int status,int po_id) {
        return attackDetailDao.findByThree(attackip,status,po_id);
    }

    public void update(AttackDetailEntity e) {
         attackDetailDao.update(e);
    }
    /*******************************************************************************************/

    public List<AttackDetailEntity> getAllAttackDefenseInfo() {
        return attackDetailDao.findAll();
    }

    public AttackDetailEntity getAttackDetailInfoById(int id) {
        return attackDetailDao.findById(id);
    }

    public void addAttackDetail(AttackDetailEntity attackDetailEntity) {
        attackDetailDao.insert(attackDetailEntity);
    }

    public List<AttackDetailEntity> getByAttackIpId(int attackip_id) {
        return attackDetailDao.findByAttackIpId(attackip_id);
    }


    public void delAll() {
        attackDetailDao.deleteAll();
    }

    protected List<DetailAttackInfo> getDetailAttackInfos(List<AttackDetailEntity> attackDetailEntities) {
        List<DetailAttackInfo> list = new ArrayList<DetailAttackInfo>();

        for (AttackDetailEntity f : attackDetailEntities) {
            ProtectObjectEntity poEntity = protectObjectDao.findById(f.getPoId());
            DetailAttackInfo detailAttackInfo = new DetailAttackInfo();
            detailAttackInfo.setDuration(secToTime(f.getDuration()));
            detailAttackInfo.setPeak(f.getPeak());
            detailAttackInfo.setStartTime(transFormDateToString(f.getStartTime(), 0));
            detailAttackInfo.setEndTime(transFormDateToString(f.getStartTime(), f.getDuration()));
            detailAttackInfo.setStatus(f.getStatus());
            detailAttackInfo.setTotalBytes(f.getTotalBytes());
            detailAttackInfo.setTotalPkts(f.getTotalPkts());
            if (poEntity != null) {
                detailAttackInfo.setPoName(poEntity.getName());
                detailAttackInfo.setPoIp(poEntity.getNetWork());
            } else {
                detailAttackInfo.setPoName("unname");
                detailAttackInfo.setPoIp("null");
            }

            detailAttackInfo.setAttackIp(f.getAttackip());
            detailAttackInfo.setAttackType(f.getAttackType());

            list.add(detailAttackInfo);
        }

        return list;
    }

    public List<DetailAttackInfo> getstat(int start, int limit) {
        List<AttackDetailEntity> attackDetailEntities = attackDetailDao.findOrderedAscByStartTime(start, limit);
        if (attackDetailEntities == null) {
            return null;
        }

        return getDetailAttackInfos(attackDetailEntities);
    }

    protected List<AttackDetailEntity> getstatOrderdeAscByStartTime(List<AttackDetailEntity> attackDetailEntities) {
        List<AttackDetailEntity> ret = new ArrayList<AttackDetailEntity>();

        for (AttackDetailEntity ade : attackDetailEntities) {
            int i = 0;
            for (AttackDetailEntity tmp : ret) {
                if (tmp.getStartTime().before(ade.getStartTime())) {
                    ret.add(i, ade);
                    break;
                }

                i++;
            }

            if (i + 1 >= ret.size()) {
                ret.add(ade);
            }
        }

        return ret;
    }

    public List<DetailAttackInfo> getstatByUserId(int userId, int start, int limit) {
        List<AttackDetailEntity> attackDetailEntities = getAttackDetailByUserId(userId);

        List<AttackDetailEntity> adeAsc = getstatOrderdeAscByStartTime(attackDetailEntities);
        List<AttackDetailEntity> ret;
        if (start > (adeAsc.size() - 1)) {
            return null;
        }

        if (adeAsc.size() > (start + limit)) {
            adeAsc = adeAsc.subList(start, start + limit);
        } else {
            adeAsc = adeAsc.subList(start, adeAsc.size());
        }

        return getDetailAttackInfos(adeAsc);
    }

    public long countAttackDetailStat() {
        return attackDetailDao.countAll();
    }

    public long countAttackDetailStatByUserId(int userId) {
        return getAttackDetailByUserId(userId).size();
    }

    public  String transFormDateToString (Date date, long duration) {
        Date time = new Date(date.getTime() + duration * 1000);//duration单位为秒，需要转换为毫秒
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(time);
        return  str;
    }

    /*
          * 毫秒转化时分秒毫秒
          */
    public static String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Long.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
//                if (hour > 99)
//                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public List<DetailAttackInfo> getAllAttackDetailInfo() {

        List<DetailAttackInfo> list = new ArrayList<DetailAttackInfo>();
        List<AttackDetailEntity> attackDetailEntities = attackDetailDao.getAllAttackDetailInfo();
        if (attackDetailEntities == null) {
            return null;
        }

        for (AttackDetailEntity f : attackDetailEntities) {

            ProtectObjectEntity poEntity = protectObjectDao.findById(f.getPoId());
            if (poEntity == null) {
                continue;
            }

            DetailAttackInfo detailAttackInfo = new DetailAttackInfo();
            detailAttackInfo.setPoName(poEntity.getName());
            detailAttackInfo.setPoIp(poEntity.getNetWork());
            detailAttackInfo.setAttackIp(f.getAttackip());
            detailAttackInfo.setAttackType(f.getAttackType());
            detailAttackInfo.setDuration(secToTime(f.getDuration()));//数据库保存单位为秒，需要转换为毫秒
            detailAttackInfo.setPeak(f.getPeak());
            detailAttackInfo.setStartTime(transFormDateToString(f.getStartTime(), 0));
            detailAttackInfo.setEndTime(transFormDateToString(f.getStartTime(),f.getDuration()));
            detailAttackInfo.setStatus(f.getStatus());
            detailAttackInfo.setTotalBytes(f.getTotalBytes());
            detailAttackInfo.setTotalPkts(f.getTotalPkts());

            list.add(detailAttackInfo);
        }

        return list;
    }

    public List<DetailAttackDefenseInfo> getAttackDefenseInfoByTime() {

        long endTime = System.currentTimeMillis();
        long timeInterval = 3600 * 1000 * 24;
        long checkInterval = 3600 * 1000;
        long startTime = endTime - timeInterval;
        long checkTime = startTime + checkInterval * 2;
        Date endDate;
        Date startDate;
        Date checkDate;
        Timestamp endTimeStamp;
        Timestamp startTimeStamp;
        Timestamp checkTimeStamp;

        endTimeStamp = new Timestamp(endTime);
        endDate = endTimeStamp;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        //startDate = new Date(start_time);
        startTimeStamp = new Timestamp(startTime);
        startDate = startTimeStamp;

        checkTimeStamp = new Timestamp(checkTime);
        checkDate = checkTimeStamp;
        List<DetailAttackDefenseInfo> list = new ArrayList<DetailAttackDefenseInfo>();
        for (int i=0;i<12;i++) {
            DetailAttackDefenseInfo detailAttackDefenseInfo = new DetailAttackDefenseInfo();
            List<AttackDetailEntity> attackDetailEntities = attackDetailDao.findAttackDetailByTime(startDate, checkDate);
            if (attackDetailEntities == null) {
                startTime = checkTime;
                checkTime = checkTime + checkInterval * 2;
                startTimeStamp = new Timestamp(startTime);
                startDate = startTimeStamp;

                checkTimeStamp = new Timestamp(checkTime);
                checkDate = checkTimeStamp;
                continue;
            }
            detailAttackDefenseInfo.setDefenseCount(attackDetailEntities.size());

            for (AttackDetailEntity f : attackDetailEntities) {
                detailAttackDefenseInfo.setMaxBps(detailAttackDefenseInfo.getMaxBps() + f.getPeak());
            }
            detailAttackDefenseInfo.setSaveTime(attackDetailEntities.get(attackDetailEntities.size()/2).getStartTime().getTime());
            list.add(detailAttackDefenseInfo);

            startTime = checkTime;
            checkTime = checkTime + checkInterval *2;
            startTimeStamp = new Timestamp(startTime);
            startDate = startTimeStamp;

            checkTimeStamp = new Timestamp(checkTime);
            checkDate = checkTimeStamp;
        }

        return list;
    }

    protected List<AttackDetailEntity> getAttackDetailByTimeAndUserId(int userId, Date startDate, Date checkDate) {
        List<AttackDetailEntity> attackDetailEntities = getAttackDetailByUserId(userId);
        List<AttackDetailEntity>  ret = new ArrayList<AttackDetailEntity>();

        for (AttackDetailEntity ade : attackDetailEntities) {
            if ((ade.getStartTime().compareTo(startDate) < 0) || ade.getStartTime().compareTo(checkDate) > 0) {
                continue;
            }

            ret.add(ade);
        }

        if (ret.size() == 0) {
            return null;
        } else {
            return ret;
        }
    }

    public List<DetailAttackDefenseInfo> getAttackDefenseInfoByTimeAndUserId(int userId) {
        long endTime = System.currentTimeMillis();
        long timeInterval = 3600 * 1000 * 24;
        long checkInterval = 3600 * 1000;
        long startTime = endTime - timeInterval;
        long checkTime = startTime + checkInterval * 2;
        Date endDate;
        Date startDate;
        Date checkDate;
        Timestamp endTimeStamp;
        Timestamp startTimeStamp;
        Timestamp checkTimeStamp;

        endTimeStamp = new Timestamp(endTime);
        endDate = endTimeStamp;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        //startDate = new Date(start_time);
        startTimeStamp = new Timestamp(startTime);
        startDate = startTimeStamp;

        checkTimeStamp = new Timestamp(checkTime);
        checkDate = checkTimeStamp;
        List<DetailAttackDefenseInfo> list = new ArrayList<DetailAttackDefenseInfo>();
        for (int i=0;i<12;i++) {
            DetailAttackDefenseInfo detailAttackDefenseInfo = new DetailAttackDefenseInfo();
            List<AttackDetailEntity> attackDetailEntities = getAttackDetailByTimeAndUserId(userId, startDate, checkDate);
            if (attackDetailEntities == null) {
                startTime = checkTime;
                checkTime = checkTime + checkInterval * 2;
                startTimeStamp = new Timestamp(startTime);
                startDate = startTimeStamp;

                checkTimeStamp = new Timestamp(checkTime);
                checkDate = checkTimeStamp;
                continue;
            }
            detailAttackDefenseInfo.setDefenseCount(attackDetailEntities.size());

            for (AttackDetailEntity f : attackDetailEntities) {
                detailAttackDefenseInfo.setMaxBps(detailAttackDefenseInfo.getMaxBps() + f.getPeak());
            }
            detailAttackDefenseInfo.setSaveTime(attackDetailEntities.get(attackDetailEntities.size()/2).getStartTime().getTime());
            list.add(detailAttackDefenseInfo);

            startTime = checkTime;
            checkTime = checkTime + checkInterval *2;
            startTimeStamp = new Timestamp(startTime);
            startDate = startTimeStamp;

            checkTimeStamp = new Timestamp(checkTime);
            checkDate = checkTimeStamp;
        }

        return list;
    }

    public class DetailAttackInfo {
        private String startTime;
        private String endTime;
        private String duration;
        private int status;
        private long totalPkts;
        private long totalBytes;
        private long peak;
        private int attackip_id;
        private String poName;
        private String poIp;
        private String attackIp;
        private int attackType;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTotalPkts() {
            return totalPkts;
        }

        public void setTotalPkts(long totalPkts) {
            this.totalPkts = totalPkts;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public long getPeak() {
            return peak;
        }

        public void setPeak(long peak) {
            this.peak = peak;
        }

        public int getAttackip_id() {
            return attackip_id;
        }

        public void setAttackip_id(int attackip_id) {
            this.attackip_id = attackip_id;
        }

        public String getPoName() {
            return poName;
        }

        public void setPoName(String poName) {
            this.poName = poName;
        }

        public String getPoIp() {
            return poIp;
        }

        public void setPoIp(String poIp) {
            this.poIp = poIp;
        }

        public String getAttackIp() {
            return attackIp;
        }

        public void setAttackIp(String attackIp) {
            this.attackIp = attackIp;
        }

        public int getAttackType() {
            return attackType;
        }

        public void setAttackType(int attackType) {
            this.attackType = attackType;
        }
    }

    public class AttackSrcTop10 {
        private String ip;
        private long newPeak;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public long getNewPeak() {
            return newPeak;
        }

        public void setNewPeak(long newPeak) {
            this.newPeak = newPeak;
        }
    }
    public class AttackTotalStat {
        private long attackCount;
        private long defenseCount;
        private long cleanTraffic;
        private long attackSrcNum;

        public long getAttackCount() {
            return attackCount;
        }

        public void setAttackCount(long attackCount) {
            this.attackCount = attackCount;
        }

        public long getDefenseCount() {
            return defenseCount;
        }

        public void setDefenseCount(long defenseCount) {
            this.defenseCount = defenseCount;
        }

        public long getCleanTraffic() {
            return cleanTraffic;
        }

        public void setCleanTraffic(long cleanTraffic) {
            this.cleanTraffic = cleanTraffic;
        }

        public long getAttackSrcNum() {
            return attackSrcNum;
        }

        public void setAttackSrcNum(long attackSrcNum) {
            this.attackSrcNum = attackSrcNum;
        }
    }

    public class DetailAttackDefenseInfo {
        private long maxBps;
        private long defenseCount;
        private long saveTime;


        public long getMaxBps() {
            return maxBps;
        }

        public void setMaxBps(long maxBps) {
            this.maxBps = maxBps;
        }

        public long getDefenseCount() {
            return defenseCount;
        }

        public void setDefenseCount(long defenseCount) {
            this.defenseCount = defenseCount;
        }

        public long getSaveTime() {
            return saveTime;
        }

        public void setSaveTime(long saveTime) {
            this.saveTime = saveTime;
        }
    }
}
