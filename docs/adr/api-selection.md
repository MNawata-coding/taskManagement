# ADR 0003: APIの認証・認可方式

日付: 2025-12-12
ステータス: proposed
Context
- 実務で一般的なトークンベースの外部連携を想定し、標準的な方法で保護されたAPIを示したい。
制約
- 個人開発・短期
決定（Decision）
- Spring SecurityのOAuth2 Resource Server（JWT）を採用。issuer-uriベースの自動設定で署名検証とiss/exp/nbf等の検証を行う。
代替案（評価）
- セッション+Cookie: 同一オリジンWeb向けには実務上有効だが、APIポートフォリオの訴求としては汎用性が劣るため不採用。
- APIキー: 実装は容易だが権限管理や失効、監査の観点で弱い。
影響/リスクと緩和
- 起動時にIssuerへメタデータ/JWK取得で外部可用性に依存 → jwk-set-uri直指定で回避可。(🔗 docs.spring.io)
検証方法
- ローカルで発行したJWT（正/誤署名、期限切れ）で受け入れテスト。
見直し条件
- スコープ/ロールの細分化が必要になったらPoliciyベース（ABAC）の適用を検討。