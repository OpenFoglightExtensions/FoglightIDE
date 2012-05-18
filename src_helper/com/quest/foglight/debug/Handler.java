package com.quest.foglight.debug;

import java.sql.Timestamp;
import java.util.LinkedList;

import com.quest.nitro.model.topology.TopologyObject;
import com.quest.wcf.data.wcfdo.DataObject;

public class Handler {

	private static Handler _singleton = null;
	
	public static class MsgStore {
		public MsgStore(DataObject scope, String level, String region,
				String msg){
			_scope = scope;
			_level = level;
			_region = region;
			_msg = msg;

			_date = new Timestamp(System.currentTimeMillis());
		}
		
		DataObject _scope;
		String _level;
		String _region;
		String _msg;
		Timestamp _date; 
		public DataObject getScope() {
			return _scope;
		}
		public String getLevel() {
			return _level;
		}
		public String getRegion() {
			return _region;
		}
		public String getMsg() {
			return _msg;
		}
		public Timestamp getDate() {
			return _date;
		}
		
	}
	public static Handler getInstance(){
		if (_singleton == null) _singleton = new Handler();
		return _singleton;
	}
	
	
	public synchronized void logMsg(DataObject scope, String level, String region, String msg) {
		MsgStore store = new MsgStore(scope,level,region,msg);
		_messages.addFirst(store);
		if (_messages.size() > 250) _messages = new LinkedList<MsgStore>(_messages.subList(0, 200));
	}
	
	private LinkedList<MsgStore> _messages = new LinkedList<MsgStore>();
	
	public synchronized LinkedList<MsgStore> getAllMessages() {
		return _messages;
	}
	
		
}
