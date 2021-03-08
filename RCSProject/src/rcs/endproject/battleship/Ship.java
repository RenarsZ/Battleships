package rcs.endproject.battleship;

public class Ship {
	public final String shipName;
	public final int length;
	public final int id;

	public static final int CARRIER = 6;
	public static final int DESTROYER = 4;
	public static final int CRUISER = 5;
	public static final int SUBMARINE = 3;
	public static final int FRIGATE = 2;
	public static final int SCOUT = 1;

	public Ship(int id, String shipName, int length) {
		this.id = id;
		this.shipName = shipName;
		this.length = length;

	}

	public static final Ship[] ships = new Ship[] { new Ship(0, "Frigate", Ship.FRIGATE),
			new Ship(1, "Carrier", Ship.CARRIER), new Ship(2, "Cruiser", Ship.CRUISER),
			new Ship(3, "Submarine", Ship.SUBMARINE), new Ship(4, "Destroyer", Ship.DESTROYER),
			new Ship(5, "Scout", Ship.SCOUT) };

	public String getShipName() {
		return shipName;
	}

}
