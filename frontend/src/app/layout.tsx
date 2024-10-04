import './globals.css';
import { Navbar } from '@/components/layout/navbar';

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
        {children}
      </body>
    </html>
  );
}
