package friendlyevil.controler;

import friendlyevil.model.Task;
import friendlyevil.model.TaskList;
import friendlyevil.repository.TaskListRepository;
import friendlyevil.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @GetMapping("/task-lists")
    public String getTaskLists() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        prepareModel(model, taskListRepository.findAll());
        return "task_list";
    }

    @PostMapping("/create-task-list")
    public String createTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskListRepository.save(taskList);
        return "redirect:/";
    }

    @PostMapping("/delete-task-list")
    public String deleteTaskList(@RequestParam("deleteTaskId") long id) {
        taskListRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/create-task")
    public String createTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

    @PostMapping("/change-status")
    public String completeTask(@RequestParam("taskId") long id, @RequestParam("status") boolean isComplete) {
        taskRepository.updateTaskStatus(id, isComplete);
        return "redirect:/";
    }

    private void prepareModel(Model model, List<TaskList> taskList) {
        model.addAttribute("taskLists", taskList);
        model.addAttribute("taskList", new TaskList());
        Task task = new Task();
        task.setTaskList(new TaskList());
        model.addAttribute("task", task);
    }
}
