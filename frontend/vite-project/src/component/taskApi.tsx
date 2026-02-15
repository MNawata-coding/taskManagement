import type { Task } from "./task";

// 削除用api
export async function DeleteTask(id: number) {
  const deleteUrl = "http://localhost:8080/test/delete/";
  try {
    // url + idで削除するIDを指定する
    await fetch(deleteUrl + id, {
      method: "DELETE",
    });
  } catch (error) {
    console.log(error);
  }
}

export async function GetTask() {
  const getUrl = "http://localhost:8080/test/getAll";
  try {
    // バックエンドから値を取得する
    const response = await fetch(getUrl);
    // 取得した値を設定
    return (await response.json()) as Task[];
  } catch (error) {
    console.log(error);
    return [];
  }
}

export async function SaveTask(task: Pick<Task, "taskName">) {
  const url = "http://localhost:8080/test/save";
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ taskName: task.taskName }),
    });
    if (!response.ok) {
      console.log("access failed");
      throw new Error(`error:${response.status}`);
    }
  } catch (error) {
    console.log(error);
  }
}
