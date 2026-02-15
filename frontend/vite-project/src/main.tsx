import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import MyApp from './component/taskSave.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
    <MyApp />
  </StrictMode>,
)
