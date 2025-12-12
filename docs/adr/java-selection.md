# ADR 0001: 実行基盤（Java と Spring Boot）のベースライン

Date: 2025-12-12
ステータス: proposed
Context
- ポートフォリオとして「現行のLTS Javaでのサーバサイド開発力」を示す。
- Spring Boot 4 の要件を満たし、維持しやすい依存構成にする。根拠: Boot 4 は Java 17+ 必須、Gradle 8.14+ / 9.x をサポート。(🔗 docs.spring.io)

制約
- 個人開発/短期間、CI/CDは無料枠、コンテナ実行想定。
- AWS上で実行できる想定

決定（Decision）
- Java 21（LTS）＋ Spring Boot 4を採用。
- Java 21の仮想スレッドを必要に応じて学習する。

代替案（評価）
- Java 17 + Spring Boot 4: 最小要件を満たし保守性も高いが、仮想スレッド（21で標準化）実証の価値が下がる。
- 他フレームワーク（例: Micronaut/Quarkus + Java 21）: 起動/メモリで優位なケースもあるが、Springエコシステム（Security/Actuator/Cloud）との統合実績を優先して不採用。
- 影響/リスクと緩和
- 仮想スレッド特有の“ピン止め”でスループットが下がる場合がある。JFRで確認し問題時は無効化し従来スレッドへフォールバック。keep-aliveを有効化してJVM終了を防ぐ。

検証方法
- Boot 4 の要件を満たすビルド（Gradle 8.14+）、起動ログでJava 21を確認、仮想スレッドON/OFFのJFR比較をREADMEに添付。
- 見直し条件
- BootのマイナーアップグレードやJDKの次期LTS到来時に再評価。