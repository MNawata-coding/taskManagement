# ADR 0002: フロントエンド技術選定（UIの有無と選択肢）

日付: 2025-12-12
ステータス: proposed
Context
- APIだけでなく「クライアント視点の体験」と型安全なUI開発スキルを示したい。
制約
- 個人開発、短期、SSRは必須ではない。
決定（Decision）
- React + TypeScript（Vite）を採用し、API仕様（OpenAPI）に基づく型生成で疎通デモを実装。Reactは公式にTypeScript連携が整理されており、学習/実務の汎用性が高い。

代替案（評価）
- Vue 3 + TypeScript: 学習コストは低めで開発効率良好。既存経験や採用先の技術嗜好次第では有力。今回はReactの実務露出を優先。
- SvelteKit: 体験は良いが実務での露出やチーム標準の観点で今回の目的に対する訴求が弱い。
影響/リスクと緩和
- Node/npmの依存管理が増える → Renovateとロックファイルで安定化。
- 検証方法
- OpenAPIから型を生成し、型安全なAPI呼び出しを1画面実装（POST/GET）。
見直し条件
- UI要件が増しSSR/SEOが必要になればNext.js採用を再検討。