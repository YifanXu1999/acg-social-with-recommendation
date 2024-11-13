export default function Template({ children }: { children: React.ReactNode }) {
    console.log('login template')
    return <div className="login-template">{children}</div>
  }