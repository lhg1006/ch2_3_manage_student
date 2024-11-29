package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseCommandRepositoryImpl;
import org.fastcampus.student_management.repo.CourseJdbcCommandRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.UserInputType;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;

public class Main {

  public static void main(String[] args) {
    StudentRepository studentRepository = new StudentRepository();
    CourseCommandRepositoryImpl courseRepository = new CourseCommandRepositoryImpl();
    CourseJdbcCommandRepository jdbcCommandRepository = new CourseJdbcCommandRepository();

    StudentService studentService = new StudentService(studentRepository);
    CourseService courseService = new CourseService(courseRepository, jdbcCommandRepository, studentRepository);

    CoursePresenter coursePresenter = new CoursePresenter();
    StudentPresenter studentPresenter = new StudentPresenter();

    CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
    StudentController studentController = new StudentController(studentPresenter, studentService);

    StudentInfoDto studentInfoDto = new StudentInfoDto("Hong", 20, "Gang-Nam");
    StudentInfoDto studentInfoDto1 = new StudentInfoDto("Jhon", 21, "No-Won");

    studentService.saveStudent(studentInfoDto);
    studentService.saveStudent(studentInfoDto1);

    CourseInfoDto courseInfoDto = new CourseInfoDto("violin", 1000, "MONDAY", "Hong",1717299008L);
    CourseInfoDto courseInfoDto1 = new CourseInfoDto("chello", 1200, "MONDAY", "Jhon", 1717299008L);
    courseService.registerCourse(courseInfoDto);
    courseService.registerCourse(courseInfoDto1);

    studentPresenter.showMenu();
    UserInputType userInputType = studentController.getUserInput();
    while (userInputType != UserInputType.EXIT) {
      switch (userInputType) {
        case NEW_STUDENT:
          studentController.registerStudent();
          break;
        case NEW_COURSE:
          courseController.registerCourse();
          break;
        case SHOW_COURSE_DAY_OF_WEEK:
          courseController.showCourseDayOfWeek();
          break;
        case ACTIVATE_STUDENT:
          studentController.activateStudent();
          break;
        case DEACTIVATE_STUDENT:
          studentController.deactivateStudent();
          break;
        case CHANGE_FEE:
          courseController.changeFee();
          break;
        default:
          studentPresenter.showErrorMessage();
          break;
      }
      studentPresenter.showMenu();
      userInputType = studentController.getUserInput();
    }
  }
}