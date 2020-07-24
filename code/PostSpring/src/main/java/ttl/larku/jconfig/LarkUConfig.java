package ttl.larku.jconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.dao.jpahibernate.JpaCourseDAO;
import ttl.larku.dao.jpahibernate.JpaStudentDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

@Configuration
@ComponentScan({"ttl.larku.service", "ttl.larku.dao"})
@PropertySource({"classpath:/larkUContext.properties"})
public class LarkUConfig {

    @Autowired
    private Environment env;

    @Bean
    @Profile("development")
    public BaseDAO<Student> studentDAO() {
        return inMemoryStudentDAO();
    }

    @Bean(name = "studentDAO")
    @Profile("production")
    public BaseDAO<Student> studentDAOJpa() {
        return jpaStudentDAO();
    }

    @Bean
    @Profile("development")
    public BaseDAO<Course> courseDAO() {
        return inMemoryCourseDAO();
    }

    @Bean(name = "courseDAO")
    @Profile("production")
    public BaseDAO<Course> courseDAOJPA() {
        return jpaCourseDAO();
    }
	/*
	@Bean
	public BaseDAO<Student> studentDAO() {
		//String profile = env.getProperty("larku.profile.active");
		switch(profile) {
		case "development":
			return inMemoryStudentDAO();
		case "production":
			return jpaStudentDAO();
		}
		return inMemoryStudentDAO();
	}

	@Bean 
	public BaseDAO<Course> courseDAO() {
		//String profile = env.getProperty("larku.profile.active");
		switch(profile) {
		case "development":
			return inMemoryCourseDAO();
		case "production":
			return jpaCourseDAO();
		}
		return inMemoryCourseDAO();
	}
	*/

    @Bean
    public BaseDAO<Student> inMemoryStudentDAO() {
        return new InMemoryStudentDAO();
    }

    @Bean
    public BaseDAO<Student> jpaStudentDAO() {
        return new JpaStudentDAO();
    }


    @Bean
    public StudentService studentService() {
        Object obj = this;
        StudentService ss = new StudentService();
        ss.setStudentDAO(studentDAO());

        return ss;
    }

    @Bean
    public CourseService courseService() {
        CourseService cc = new CourseService();
        cc.setCourseDAO(courseDAO());

        return cc;
    }


    @Bean
    public BaseDAO<Course> inMemoryCourseDAO() {
        return new InMemoryCourseDAO("Mem");
    }

    @Bean
    public BaseDAO<Course> jpaCourseDAO() {
        return new JpaCourseDAO("JPA");
    }
}