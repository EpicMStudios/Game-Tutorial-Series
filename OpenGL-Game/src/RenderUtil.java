
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import org.lwjgl.util.vector.Vector3f;
public class RenderUtil {

	public static void clearScreen(){
		
		//TODO: STENCIL BUFFER
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
	}
	public static void setTextures(boolean enabled){
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}
	public static void initGraphics(){
		
		glClearColor(0.0f,0.0f,0.0f,1.0f);
		
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		//glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_CLAMP);

		
	}

	public static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public static void setClearColor(Vector3f color, float alpha)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), alpha);
	}
	public static String getOpenGLVersion(){
		return glGetString(GL_VERSION);
	}
}

