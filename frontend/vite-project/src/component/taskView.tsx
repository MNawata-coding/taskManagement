import type { TaskList } from "./props";

export default function TaskView(props: TaskList) {

  return (
    <div>
      {props.tasks.map((task) => (
        <div key={task.id}>
          <li>
            {task.id}:{task.taskName}/{task.createDate}
            <button type="button" onClick={() => props.onDeleteTask(task.id)}>
              削除
            </button>
          </li>
        </div>
      ))}
    </div>
  );
}

