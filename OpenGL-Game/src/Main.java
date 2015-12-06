

public class Main {

	public static void main(String[] args){
		Core core = new Core(new Game(), 640, 480, 60);
		core.createWindow("Game Window");
		core.start();
	}

}

