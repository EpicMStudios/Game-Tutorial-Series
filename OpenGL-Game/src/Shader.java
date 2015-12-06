import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.Util;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Shader {
	private int program;
	private HashMap<String, Integer> uniforms;



	public Shader(){
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if (program == 0){
			System.err.println("SHADER CREATION FAILED. COULD NOT FIND VALID MEMORY LOCATION IN CONSTRUCTOR");
			System.exit(1);
		}
	}
	public void clearShader(){
		
	}
	public void addVertexShader(String Text){
		addProgram(Text,GL_VERTEX_SHADER);
	}
	public void addFragmentShader(String Text){
		addProgram(Text,GL_FRAGMENT_SHADER);
	}
	public void addGeometryShader(String Text){
		addProgram(Text,GL_GEOMETRY_SHADER);
	}
	public void compileShader(){
		System.out.println("COMPILING SHADER:"+program);
		glLinkProgram(program);
		if(glGetProgrami(program,GL_LINK_STATUS) == 0){
			System.err.println("SHADER COMPILATION FAILED: GL_LINK_STATUS");
			System.err.println(glGetProgramInfoLog(program, GL_INFO_LOG_LENGTH));
			System.exit(1);
			}
		if(glGetProgrami(program,GL_VALIDATE_STATUS) == 0){
			System.err.println("SHADER COMPILATION FAILED: GL_VALIDATE_STATUS");
			System.err.println(glGetProgramInfoLog(program, GL_INFO_LOG_LENGTH));
			System.exit(1);
			}
	}

	public void bind(){
		glUseProgram(program);
	}
	public void unbind()
	{
	glUseProgram(0);
	}
	public void addUniform(String uniform){
		int UniformLocation = glGetUniformLocation(program, uniform);
		
		if (UniformLocation == 0xFFFFFFFF){
			System.err.println("SHADER ERROR: Could Not Find Uniform " + uniform);
			new Exception().printStackTrace();
			//System.exit(1);
			uniforms.put(uniform, 0);
		}
		
		uniforms.put(uniform, UniformLocation);
		
	}
	private void addProgram(String Text, int type){
		int shader = glCreateShader(type);
		if (shader == 0){
			System.err.println("SHADER CREATION FAILED. COULD NOT FIND VALID MEMORY LOCATION WHEN ADDING SHADER");
			System.exit(1);
		}
		glShaderSource(shader,Text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader,GL_COMPILE_STATUS) == 0){
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
			}
		glAttachShader(program,shader);
		glDeleteShader(shader);
	}

	public void setUniformi(String uniformName,int value){
		glUniform1i(uniforms.get(uniformName),value);
	}

	public void setUniformf(String uniformName,float value){
		glUniform1f(uniforms.get(uniformName),value);
	}

	public void setUniform2f(String uniformName,float valueX,float valueY){
		glUniform2f(uniforms.get(uniformName),valueX,valueY);
	}

	public void setUniform3f(String uniformName, float valueX, float valueY, float valueZ){
		glUniform3f(uniforms.get(uniformName),valueX,valueY,valueZ);
	}
	public void setUniform3f(String uniformName, Vector3f vector){
		glUniform3f(uniforms.get(uniformName),vector.getX(),vector.getY(),vector.getZ());
	}
	public void setUniformM4f(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, createFlippedBuffer(value));
	}
	public int getProgramID(){
		return program;
	}
	public String load(String fileName, boolean isExternal){
		StringBuilder ShaderString = new StringBuilder();
		BufferedReader ShaderReader = null;
		
		 try {
			if(isExternal)ShaderReader = new BufferedReader(new FileReader( "./Shaders/" + fileName));
			else ShaderReader = new BufferedReader(new InputStreamReader(Shader.class.getResourceAsStream(("/Shaders/"+fileName))));
			String Line;

				while((Line = ShaderReader.readLine()) != null){
					ShaderString.append(Line).append("\n");
				}
				ShaderReader.close();
				}
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return ShaderString.toString();
	}
	public static FloatBuffer createFlippedBuffer(Matrix4f value)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		value.store(buffer);
		buffer.flip();
		return buffer;
	}
}