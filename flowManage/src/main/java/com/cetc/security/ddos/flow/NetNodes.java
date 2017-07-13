package com.cetc.security.ddos.flow;

import java.util.HashMap;
import java.util.Map;

public class NetNodes {
	private Map<Integer, NetNode> netNodes;
	
	public NetNodes()
	{
		netNodes = new HashMap<Integer, NetNode>();
	}

    public Map<Integer, NetNode> getNetNodes() {
        return netNodes;
    }

    public void addNode(NetNode item) {
        netNodes.put(item.getNetNodeEntity().getId(), item);
	}

    public NetNode delNode(int id) {
        NetNode item = netNodes.get(id);
        if (item == null) {
            return null;
        }

        return delNode(item);
    }
	
	public NetNode delNode(NetNode item)
	{
		item.delAllPO();
		return netNodes.remove(item.getNetNodeEntity().getId());
	}

    /*
	public void delAllNetNode(NetNode item)
	{
		int i;
		for (i=0;i<netNodes.size();i++)
		{
			delNode(netNodes.get(i));
		}
		netNodes.clear();
	}
	*/
}
