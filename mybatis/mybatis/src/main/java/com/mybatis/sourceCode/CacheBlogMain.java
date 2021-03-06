package com.mybatis.sourceCode;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mybatis.mapper.BlogMapper;
import com.mybatis.vo.Blog;

public class CacheBlogMain {
	public static void main(String[] args) throws IOException {
		String resource = "mybatis-config-sourceCode.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		SqlSession session = sqlSessionFactory.openSession();
		try {
			BlogMapper<Blog> mapper = session.getMapper(BlogMapper.class);
			Blog blog = mapper.selectBlog(160);
			System.out.println(blog);
			// 默认开启一级缓存，在参数和sql相同的情况下，只执行一次sql
			System.out.println(mapper.selectBlog(160));
		} finally {
			session.close();
		}

		SqlSession session2 = sqlSessionFactory.openSession();
		try {
			BlogMapper<Blog> mapper = session2.getMapper(BlogMapper.class);
			Blog blog2 = mapper.selectBlog(160);
			System.out.println(blog2);
		} finally {
			session2.close();
		}

	}

}
