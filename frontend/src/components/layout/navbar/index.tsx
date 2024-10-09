import LogoSquare from '../../logo-square'
import Link from 'next/link';


export function Navbar() {
  return (
    <nav className="relative max-h-5 flex items-center justify-between p-4 lg:px-6 bg-white">
      <div className="flex w-full items-center">
        <div className="flex w-full md:w-1/3">
          <Link
            href="/"
            className="mr-2 flex w-full items-center justify-center md:w-auto lg:mr-6"
          >
            <LogoSquare />
          </Link>
        </div>
      </div>
    </nav>
  );
}