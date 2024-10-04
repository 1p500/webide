import LogoIcon from './icons/logo';

export default function LogoSquare({ size }: { size?: 'sm' | undefined }) {
  return (
    <div
      className={
        size === 'sm'
          ? 'flex flex-none items-center justify-center h-[30px] w-[30px] '
          : 'flex flex-none items-center justify-center h-[40px] w-[40px] '
      }
    >
      <LogoIcon
        className={size === 'sm' ? 'h-[10px] w-[10px]' : 'h-[16px] w-[16px]'}
      />
    </div>
  );
}
