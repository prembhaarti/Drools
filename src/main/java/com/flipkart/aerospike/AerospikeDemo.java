package com.flipkart.aerospike;
import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.WritePolicy;

import java.util.ArrayList;
import java.util.List;

public class AerospikeDemo {

	public static final AerospikeClient client = getClient();

	public static void main(String[] args) {

//		writeData();
		operations();

		client.close();
	}

	public static AerospikeClient getClient(){
		Host[] hosts = new Host[] {
				new Host("10.33.105.188", 3000)
		};

		ClientPolicy policy = new ClientPolicy();
//		policy.user = "myuser";
//		policy.password = "mypass";
		AerospikeClient client = new AerospikeClient(policy, hosts);
		System.out.println("Client connection status:"+client.isConnected());
		return client;
	}

	public static void writeData(){
		WritePolicy writePolicy = new WritePolicy();
		writePolicy.timeout = 5000;
		// Write single value.
		Key key = new Key("test", "demo", "toa:2");
		Bin account_id = new Bin("account_id", 2);
		Bin pcsa = new Bin("pcsa", 150);
		client.put(writePolicy, key, account_id,pcsa);
	}

	public static void processOperations(List<Operation> operations){
		String binName = "pcsa";
		Key key = new Key("test", "demo", "toa:2");
//		Record record = client.operate(null, key, Operation.add(new Bin(binName, 1)), Operation.get(binName),Operation.get("account_id"));
		Record record = client.operate(null, key, operations.stream().toArray(Operation[]::new));
		long counterValue =  record.getLong(binName);
		long accountId = record.getLong("account_id");
		System.out.println(record.toString());
		System.out.println("pcsa:"+counterValue);
		System.out.println("account_id:"+accountId);
	}
	public static void operations(){
		List<Operation> operations = new ArrayList<>();
		operations.add(Operation.add(new Bin("pcsa", 1)));
		operations.add(Operation.get("pcsa"));
		operations.add(Operation.get("account_id"));
		processOperations(operations);
	}
	public static Record operations(String userKey, List<Operation> operations){
		Record record =
	}

}
