import type {Task} from './task'

export type TaskList = {
    tasks : Task[];
    onDeleteTask: (id: number) => void;
}