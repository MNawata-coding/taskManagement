# DB migration(Flyway)

目的：スキーマ変更をコードと同様にレビュー自動適用する

決定事項
- 置き場所：backend/src/main/resources/db/migration
- 命名:V<number>__name_table.sql(例 V1__create_task.sql)

手順
- ローカル：./gradlew :backend:bootRun（Spring Boot起動時に自動適用）
- テスト：./gradlew :backend:test（Testcontainers→Flyway→テスト）
- CI：backendジョブで ./gradlew clean test（マイグレーションも実行）