package ttl.larku.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.dao.repository.CourseRepo;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(scripts = {"/schema-h2production.sql", "/data-h2production.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClassRepoTest {

    private String name1 = "Bloke";
    private String name2 = "Blokess";
    private String newName = "Different Bloke";

    private String code1 = "BOT-101";
    private String code2 = "BOT-202";
    private String title1 = "Intro To Botany";
    private String title2 = "Advanced Basket Weaving";

    private String startDate1 = "10/10/2012";
    private String startDate2 = "10/10/2013";
    private String endDate1 = "05/10/2013";
    private String endDate2 = "05/10/2014";

    private Course course1;
    private Course course2;
    private ScheduledClass class1;
    private ScheduledClass class2;

    @Resource(name = "classRepo")
    private ClassRepo dao;

    @Resource(name = "courseRepo")
    private CourseRepo courseDAO;

    @Before
    public void setup() {
        course1 = new Course(code1, title1);
        course1 = courseDAO.save(course1);
        course2 = new Course(code2, title2);
        course2 = courseDAO.save(course2);

        class1 = new ScheduledClass(course1, startDate1, endDate1);
        class2 = new ScheduledClass(course2, startDate2, endDate2);
    }

    @Test
    public void testGetAll() {
        List<ScheduledClass> classes = dao.findAll();
        assertEquals(3, classes.size());
        System.out.println(classes);
    }

    @Test
    public void testGetStudentsByCourseCodeAndStartDate() {
        List<ScheduledClass> classes = dao.getByCourseCodeAndStartDateForStudents("BOT-202", "2012-10-10");
        List<Student> students = classes.get(0).getStudents();
        System.out.println(students);

        assertEquals(1, classes.size());
        assertEquals(1, students.size());
    }

    @Test
    public void testCreate() {

        int newId = dao.save(class1).getId();

        ScheduledClass result = dao.findById(newId).orElse(null);

        assertEquals(newId, result.getId());
    }

    @Test
    public void testUpdate() {
        int newId = dao.save(class1).getId();

        ScheduledClass result = dao.findById(newId).orElse(null);

        assertEquals(newId, result.getId());

        result.setCourse(course2);
        dao.save(result);

        result = dao.findById(newId).orElse(null);
        assertEquals(title2, result.getCourse().getTitle());
    }

    @Test
    public void testDelete() {
        int id1 = dao.save(class1).getId();

        ScheduledClass resultClass = dao.findById(id1).orElse(null);
        assertEquals(resultClass.getId(), id1);

        dao.delete(resultClass);

        resultClass = dao.findById(id1).orElse(null);

        assertEquals(null, resultClass);
    }
}