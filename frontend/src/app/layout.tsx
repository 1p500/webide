// src/app/layout.tsx
import './globals.css';
import { Navbar } from '@/components/layout/navbar';
import ReactQueryProvider from '@/components/reactQueryProvider'; // Client Component로 만든 ReactQueryProvider

export const metadata = {
  title: 'Next.js + TypeScript + TailwindCSS',
  description: 'Test setup',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        {/* React Query Provider를 감싸서 하위 컴포넌트들이 React Query 사용 가능 */}
        <ReactQueryProvider>
          {children}
        </ReactQueryProvider>
      </body>
    </html>
  );
}
