import { useState } from "react";
import type { FormEvent } from "react";
import { SaveTask } from "./taskApi";


// functionで定義することでjavaのメソッド、関数のようなものを作成する
// return はreactによって<button>などのタグをreactで使用できるように変換してくれるもの
function TaskForm() {
  const [taskName, setTaskName] = useState("");
  const [email, setEmail] = useState("");

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await SaveTask({ taskName });
    setTaskName("");
  };

  //typesの場合は以下のように宣言
  // const [enabled, setEnabled] = useState(false);
  return (
    <div>
      <form onSubmit={handleSubmit}>
        taskName
        <input type="text" value={taskName} onChange={(e) => setTaskName(e.target.value)} />
        Email
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        <button type="submit">送信</button>
      </form>
      <div>
        taskName:{taskName}
        <br />
        email:{email}
        <br />
      </div>
    </div>
  );
}

// export defaultで指定したものをimportして使用できる
// <MyButton /> で呼び出し かつ 引数をtitle=, dialbled=で指定することができる
export default function MyApp() {
  return (
    <div>
      <h1>welcome to my app</h1>
      <TaskForm />
    </div>
  );
}
