package com.cetc.security.ddos.persistence.service;


import com.cetc.security.ddos.persistence.InputTrafficInfoEntity;


import com.cetc.security.ddos.persistence.dao.InputTrafficInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;


@Service("inputTrafficService")
public class InputTrafficInfoService {

	@Autowired
	private InputTrafficInfoDao inputTrafficDao;


	public List<InputTrafficInfoEntity> getAllInputTrafficInfo() {
        return inputTrafficDao.findAll();
    }


    public InputTrafficInfoEntity getInputTrafficInfoById(int id) {
        return inputTrafficDao.findById(id);
    }

    public InputTrafficInfoEntity findInputTrafficInfoByProtocol(int protocol) {
            return inputTrafficDao.findByProtocol(protocol);
    }

    public void addInputTrafficInfo(InputTrafficInfoEntity inputTrafficinfoEntity) {
        inputTrafficDao.insert(inputTrafficinfoEntity);
    }

    public void updateInputTrafficInfo(InputTrafficInfoEntity inputTrafficinfoEntity) {
        inputTrafficDao.update(inputTrafficinfoEntity);
    }

    public void delAll() {
        inputTrafficDao.deleteAll();
    }

    public List<DetailInputTrafficInfo> getInputTrafficStat() {
        List<DetailInputTrafficInfo> list = new ArrayList<DetailInputTrafficInfo>();
        List<InputTrafficInfoEntity> inputStatisticsEntitys = inputTrafficDao.findAll();

        for (InputTrafficInfoEntity f: inputStatisticsEntitys) {
            DetailInputTrafficInfo dfInfo = new DetailInputTrafficInfo();
            dfInfo.setProtocol(f.getProtocol());
            dfInfo.setRate_pps(f.getRate_pps());
            dfInfo.setRate_bps(f.getRate_bps());

            list.add(dfInfo);
        }

        return list;
    }

    public class DetailInputTrafficInfo {
        private int protocol;
        private long rate_bps;
        private long rate_pps;

        public int getProtocol() {
            return protocol;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }

        public long getRate_bps() {
            return rate_bps;
        }

        public void setRate_bps(long rate_bps) {
            this.rate_bps = rate_bps;
        }

        public long getRate_pps() {
            return rate_pps;
        }

        public void setRate_pps(long rate_pps) {
            this.rate_pps = rate_pps;
        }
    }
}
