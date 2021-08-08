package com.g20210579050111.chapter1homework2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chapter1Homework2Application {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		SpringApplication.run(Chapter1Homework2Application.class, args);

		Class<?> clz = new XlassLoader().findClass("Hello");
		Object obj = clz.newInstance();
		Method m = clz.getMethod("hello");
		m.invoke(obj);
	}

	public static class XlassLoader extends ClassLoader {
		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			byte[] bytes = null;
			try (InputStream f = getSystemResourceAsStream("Hello.xlass")) {
				bytes = new byte[f.available()];
			    f.read(bytes, 0, f.available());
			} catch (IOException e) {
				throw new ClassNotFoundException("xlass not found", e);
			}

			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = (byte) (255 - bytes[i]);
			}

			//这里还做了一步是: 把bytes数组保存成Hello.class, 然后 java -c Hello.class反编译确定类名和hello()方法定义

			return defineClass(name,bytes,0,bytes.length);
		}
	}
}