export const UserIcon = () => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g clipPath="url(#clip0_80_1168)">
        <path
          d="M9.99916 11.4286C11.9716 11.4286 13.5706 9.82958 13.5706 7.85713C13.5706 5.88469 11.9716 4.28571 9.99916 4.28571C8.02672 4.28571 6.42773 5.88469 6.42773 7.85713C6.42773 9.82958 8.02672 11.4286 9.99916 11.4286Z"
          stroke="black"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M3.89941 17C4.5369 15.9536 5.43286 15.0887 6.50114 14.4887C7.56943 13.8886 8.77411 13.5734 9.99941 13.5734C11.2247 13.5734 12.4294 13.8886 13.4977 14.4887C14.566 15.0887 15.4619 15.9536 16.0994 17"
          stroke="black"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
        <path
          d="M9.99958 19.2857C15.128 19.2857 19.2853 15.1284 19.2853 10C19.2853 4.87165 15.128 0.714294 9.99958 0.714294C4.87122 0.714294 0.713867 4.87165 0.713867 10C0.713867 15.1284 4.87122 19.2857 9.99958 19.2857Z"
          stroke="black"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      </g>
      <defs>
        <clipPath id="clip0_80_1168">
          <rect width="20" height="20" fill="#FFF" />
        </clipPath>
      </defs>
    </svg>
  );
};

export const LockIcon = () => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M15.7146 7.85706H4.28599C3.49702 7.85706 2.85742 8.49666 2.85742 9.28563V17.8571C2.85742 18.6461 3.49702 19.2856 4.28599 19.2856H15.7146C16.5036 19.2856 17.1431 18.6461 17.1431 17.8571V9.28563C17.1431 8.49666 16.5036 7.85706 15.7146 7.85706Z"
        stroke="black"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M15 7.85709V5.71423C15 4.38815 14.4733 3.11638 13.5355 2.1787C12.5979 1.24102 11.3261 0.714233 10 0.714233C8.67391 0.714233 7.40214 1.24102 6.46447 2.1787C5.52679 3.11638 5 4.38815 5 5.71423V7.85709"
        stroke="black"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
      <path
        d="M10.0004 14.2856C10.3949 14.2856 10.7147 13.9658 10.7147 13.5713C10.7147 13.1769 10.3949 12.8571 10.0004 12.8571C9.60593 12.8571 9.28613 13.1769 9.28613 13.5713C9.28613 13.9658 9.60593 14.2856 10.0004 14.2856Z"
        stroke="black"
        strokeLinecap="round"
        strokeLinejoin="round"
      />
    </svg>
  );
};

export const FileAddTransparent = ({ className }: { className?: string }) => {
  return(
  <svg viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"
  className={className}>
    <g clipPath="url(#clip0_85_1174)">
      <path d="M2.5 5V1.5C2.5 1.23478 2.60536 0.98043 2.79289 0.792893C2.98043 0.605357 3.23478 0.5 3.5 0.5H10L13.5 4V12.5C13.5 12.7652 13.3946 13.0196 13.2071 13.2071C13.0196 13.3946 12.7652 13.5 12.5 13.5H7.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M3.5 7.5V13.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M0.5 10.5H6.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
    </g>
    {/* 파일 겉 사각형 */}
    {/* <rect x="0.5" y="0.5" width="13" height="13" stroke=""/> */}
    <rect x="0.5" y="0.5" width="13" height="13" stroke="currentColor" strokeOpacity="0.2"/>
    <defs>
      <clipPath id="clip0_85_1174">
        <rect width="14" height="14" fill="currentColor"/>
      </clipPath>
    </defs>
  </svg>
  )
}

export const FileAdd = ({ className }: { className?: string }) => {
  return(
  <svg viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg" className={className}>
    <g clipPath="url(#clip0_85_1181)">
      <rect width="15" height="15" fill="#FFFEFE"/>
      <path d="M3 5.5V2C3 1.73478 3.10536 1.48043 3.29289 1.29289C3.48043 1.10536 3.73478 1 4 1H10.5L14 4.5V13C14 13.2652 13.8946 13.5196 13.7071 13.7071C13.5196 13.8946 13.2652 14 13 14H8" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M4 8V14" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M1 11H7" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
    </g>
    <rect x="0.5" y="0.5" width="14" height="14" stroke="currentColor"/>
    <rect x="0.5" y="0.5" width="14" height="14" stroke="currentColor" strokeOpacity="0.2"/>
    <defs>
      <clipPath id="clip0_85_1181">
        <rect width="15" height="15" fill="currentColor"/>
      </clipPath>
    </defs>
  </svg>
  )
}

export const FolderAdd = ({ className }: { className?: string }) => {
  return(
  <svg viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"
  className={className}>
    <g clipPath="url(#clip0_85_1194)">
      <rect width="14" height="14" fill="currentColor"/>
      <path d="M7 5.5V10.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M4.5 8H9.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M0.5 12.2499V1.74994C0.5 1.48472 0.605357 1.23037 0.792893 1.04283C0.98043 0.855293 1.23478 0.749936 1.5 0.749936H5.19C5.41843 0.743281 5.64226 0.815069 5.82421 0.95335C6.00615 1.09163 6.13525 1.28806 6.19 1.50994L6.5 2.74994H12.5C12.7652 2.74994 13.0196 2.85529 13.2071 3.04283C13.3946 3.23037 13.5 3.48472 13.5 3.74994V12.2499C13.5 12.5152 13.3946 12.7695 13.2071 12.957C13.0196 13.1446 12.7652 13.2499 12.5 13.2499H1.5C1.23478 13.2499 0.98043 13.1446 0.792893 12.957C0.605357 12.7695 0.5 12.5152 0.5 12.2499Z" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
    </g>
    <rect x="0.5" y="0.5" width="13" height="13" stroke="currentColor"/>
    <rect x="0.5" y="0.5" width="13" height="13" stroke="currentColor" strokeOpacity="0.2"/>
    <defs>
      <clipPath id="clip0_85_1194">
        <rect width="14" height="14" fill="currentColor"/>
      </clipPath>
    </defs>
  </svg>
  )

}

export const FolderAddTransparent = ({ className }: { className?: string }) => {
  return (
  <svg viewBox="0 0 15 15" fill="none" xmlns="http://www.w3.org/2000/svg"
  className={className}>
    <g clipPath="url(#clip0_85_1187)">
      <path d="M7 5.5V10.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M4.5 8H9.5" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
      <path d="M0.5 12.2499V1.74994C0.5 1.48472 0.605357 1.23037 0.792893 1.04283C0.98043 0.855293 1.23478 0.749936 1.5 0.749936H5.19C5.41843 0.743281 5.64226 0.815069 5.82421 0.95335C6.00615 1.09163 6.13525 1.28806 6.19 1.50994L6.5 2.74994H12.5C12.7652 2.74994 13.0196 2.85529 13.2071 3.04283C13.3946 3.23037 13.5 3.48472 13.5 3.74994V12.2499C13.5 12.5152 13.3946 12.7695 13.2071 12.957C13.0196 13.1446 12.7652 13.2499 12.5 13.2499H1.5C1.23478 13.2499 0.98043 13.1446 0.792893 12.957C0.605357 12.7695 0.5 12.5152 0.5 12.2499Z" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round"/>
    </g>
    {/* 폴더 겉 사각형 */}
    {/* <rect x="0.5" y="0.5" width="13" height="13" stroke=""/>  */} 
    <rect x="0.5" y="0.5" width="13" height="13" stroke="currentColor" strokeOpacity="0.2"/>
    <defs>
      <clipPath id="clip0_85_1187">
        <rect width="14" height="14" fill="currentColor"/>
      </clipPath>
    </defs>
  </svg>
  )
}