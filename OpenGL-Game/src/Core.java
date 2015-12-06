
import org.lwjgl.opengl.Display;

public class Core {
	private boolean running;
	Game game;
	private int width, height, maxFPS;
	private double frameTime;
	private static final long SECOND = 1000000000L;
	
	public Core(Game game,int width, int height, int maxFPS){
		this.running = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0/maxFPS;
	}
	public void initGraphics(){
		System.out.println(RenderUtil.getOpenGLVersion());
		RenderUtil.initGraphics();
	}
	public void createWindow(String title){
		Window.createWindow(width, height, title);
		initGraphics();
	}
	
	public void start()
	{
		if(running)
			return;
		
		run();
	}
	
	public void stop()
	{
		if(!running)
			return;
		
		running = false;
	}
	private void run()
	{
		running = true;
		
		int frames = 0;
		double frameCounter = 0;

		game.init();

		double lastTime = getTime();
		double unprocessedTime = 0;
		
		while(running)
		{
			boolean render = false;
			
			double startTime = getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Display.isCloseRequested())
					stop();
				
				//Time.setDelta(frameTime);
				
				game.input((float)frameTime);

				
				game.update((float)frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				render();
				
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	public static double getTime(){
		
		return (double)System.nanoTime()/(double)SECOND;
		
	}
	private void render()
	{
		
		//long startTime = Time.getTime();
		game.render();

		Window.render();

	}
	
	private void cleanUp()
	{
		Window.dispose();
	}
}
