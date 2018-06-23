package rooms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import components.Component;
import engine.Loader;
import engine.Main;
import entity.Entity;

public class Room {
	public int room_id;
	
	private String path;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> gates;
	public boolean gateEnabled = false;
	
	public int enemyCount;
	
	private Loader loader;
	
	public Room(int room_id, String path, Loader loader) {
		this.entities = new ArrayList<Entity>();
		this.gates = new ArrayList<Entity>();
		this.path = path;
		this.enemyCount = 0;
		this.room_id = room_id;
		this.loader = loader;
	}
	
	public void loadRoom() {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(enemyCount==0)
			enableGates();
	}
	
	private void readFile() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(Room.class.getResourceAsStream(path)));
		String s;
		while((s = br.readLine()) != null) {
			String[] stuff = s.split(",");
			
			if(stuff[0].equals("player")) {
				Main.creator.getPlayer().transform.pos.x = Integer.parseInt(stuff[1]);
				Main.creator.getPlayer().transform.pos.y = Integer.parseInt(stuff[2]);
			}
			else if(stuff[0].equals("wall")) {
				entities.add(Main.creator.createWall(Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]), Integer.parseInt(stuff[3]), Integer.parseInt(stuff[4]), Integer.parseInt(stuff[5])));
			}
			else if(stuff[0].equals("enemy") && !RoomMap.visted.contains(room_id)) {
				entities.add(Main.creator.createEnemy(Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]), Integer.parseInt(stuff[3]), Integer.parseInt(stuff[4]), Integer.parseInt(stuff[5])));
				enemyCount++;
			}
			else if(stuff[0].equals("exploEnemy") && !RoomMap.visted.contains(room_id)) {
				entities.add(Main.creator.createExploEnemy(Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]), Integer.parseInt(stuff[3]), Integer.parseInt(stuff[4]), Integer.parseInt(stuff[5])));
			}
			else if(stuff[0].equals("gate")) {
				Entity gate = Main.creator.createGate(Integer.parseInt(stuff[1]), Integer.parseInt(stuff[2]), Integer.parseInt(stuff[3]), Integer.parseInt(stuff[4]), Integer.parseInt(stuff[5]), Integer.parseInt(stuff[6]));
				entities.add(gate);
				gates.add(gate);
			}
		}
		br.close();
	}
	
	public void enableGates() {
		this.gateEnabled = true;
		for(Entity e: gates) {
			float sx = e.material.getSx();
			float sy = e.material.getSy();
			e.material.setMaterial(loader.loadToVAO(sx, sy, "gateEnabled"));
		}
	}
	
	public void destory() {
		for(int i = 0;i<entities.size();i++) {
			for(Component c: entities.get(i).getComponents())
				c.toRemove = true;
		}
	}
}
