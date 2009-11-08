#define _CRT_SECURE_NO_WARNINGS

#include <stdio.h>
//#include <d3dx9.h>
#include <windows.h>

//#pragma comment(lib, "d3dx9.lib")
//#pragma comment(lib, "d3d9.lib")

#define DDSD_CAPS               0x00000001l
#define DDSD_HEIGHT             0x00000002l
#define DDSD_WIDTH              0x00000004l
#define DDSD_PITCH              0x00000008l
#define DDSD_BACKBUFFERCOUNT    0x00000020l
#define DDSD_ZBUFFERBITDEPTH    0x00000040l
#define DDSD_ALPHABITDEPTH      0x00000080l
#define DDSD_LPSURFACE          0x00000800l
#define DDSD_PIXELFORMAT        0x00001000l
#define DDSD_CKDESTOVERLAY      0x00002000l
#define DDSD_CKDESTBLT          0x00004000l
#define DDSD_CKSRCOVERLAY       0x00008000l
#define DDSD_CKSRCBLT           0x00010000l
#define DDSD_MIPMAPCOUNT        0x00020000l
#define DDSD_REFRESHRATE        0x00040000l
#define DDSD_LINEARSIZE         0x00080000l
#define DDSD_TEXTURESTAGE       0x00100000l
#define DDSD_FVF                0x00200000l
#define DDSD_SRCVBHANDLE        0x00400000l
#define DDSD_DEPTH              0x00800000l
#define DDSD_ALL                0x00fff9eel


#define DDSCAPS_RESERVED1                       0x00000001l
#define DDSCAPS_ALPHA                           0x00000002l
#define DDSCAPS_BACKBUFFER                      0x00000004l
#define DDSCAPS_COMPLEX                         0x00000008l
#define DDSCAPS_FLIP                            0x00000010l
#define DDSCAPS_FRONTBUFFER                     0x00000020l
#define DDSCAPS_OFFSCREENPLAIN                  0x00000040l
#define DDSCAPS_OVERLAY                         0x00000080l
#define DDSCAPS_PALETTE                         0x00000100l
#define DDSCAPS_PRIMARYSURFACE                  0x00000200l
#define DDSCAPS_RESERVED3					    0x00000400l
#define DDSCAPS_PRIMARYSURFACELEFT              0x00000000l
#define DDSCAPS_SYSTEMMEMORY                    0x00000800l
#define DDSCAPS_TEXTURE                         0x00001000l
#define DDSCAPS_3DDEVICE                        0x00002000l
#define DDSCAPS_VIDEOMEMORY                     0x00004000l
#define DDSCAPS_VISIBLE                         0x00008000l
#define DDSCAPS_WRITEONLY                       0x00010000l
#define DDSCAPS_ZBUFFER                         0x00020000l
#define DDSCAPS_OWNDC                           0x00040000l
#define DDSCAPS_LIVEVIDEO                       0x00080000l
#define DDSCAPS_HWCODEC                         0x00100000l
#define DDSCAPS_MODEX                           0x00200000l
#define DDSCAPS_MIPMAP                          0x00400000l
#define DDSCAPS_RESERVED2                       0x00800000l
#define DDSCAPS_ALLOCONLOAD                     0x04000000l
#define DDSCAPS_VIDEOPORT                       0x08000000l
#define DDSCAPS_LOCALVIDMEM                     0x10000000l
#define DDSCAPS_NONLOCALVIDMEM                  0x20000000l
#define DDSCAPS_STANDARDVGAMODE                 0x40000000l
#define DDSCAPS_OPTIMIZED                       0x80000000l

#define DDSCAPS2_RESERVED4                      0x00000002L
#define DDSCAPS2_HARDWAREDEINTERLACE            0x00000000L
#define DDSCAPS2_HINTDYNAMIC                    0x00000004L
#define DDSCAPS2_HINTSTATIC                     0x00000008L
#define DDSCAPS2_TEXTUREMANAGE                  0x00000010L
#define DDSCAPS2_RESERVED1                      0x00000020L
#define DDSCAPS2_RESERVED2                      0x00000040L
#define DDSCAPS2_OPAQUE                         0x00000080L
#define DDSCAPS2_HINTANTIALIASING               0x00000100L
#define DDSCAPS2_CUBEMAP                        0x00000200L
#define DDSCAPS2_CUBEMAP_POSITIVEX              0x00000400L
#define DDSCAPS2_CUBEMAP_NEGATIVEX              0x00000800L
#define DDSCAPS2_CUBEMAP_POSITIVEY              0x00001000L
#define DDSCAPS2_CUBEMAP_NEGATIVEY              0x00002000L
#define DDSCAPS2_CUBEMAP_POSITIVEZ              0x00004000L
#define DDSCAPS2_CUBEMAP_NEGATIVEZ              0x00008000L
#define DDSCAPS2_CUBEMAP_ALLFACES ( DDSCAPS2_CUBEMAP_POSITIVEX |\
                                    DDSCAPS2_CUBEMAP_NEGATIVEX |\
                                    DDSCAPS2_CUBEMAP_POSITIVEY |\
                                    DDSCAPS2_CUBEMAP_NEGATIVEY |\
                                    DDSCAPS2_CUBEMAP_POSITIVEZ |\
                                    DDSCAPS2_CUBEMAP_NEGATIVEZ )
#define DDSCAPS2_MIPMAPSUBLEVEL                 0x00010000L
#define DDSCAPS2_D3DTEXTUREMANAGE               0x00020000L
#define DDSCAPS2_DONOTPERSIST                   0x00040000L
#define DDSCAPS2_STEREOSURFACELEFT              0x00080000L
#define DDSCAPS2_VOLUME                         0x00200000L
#define DDSCAPS2_NOTUSERLOCKABLE                0x00400000L
#define DDSCAPS2_POINTS                         0x00800000L
#define DDSCAPS2_RTPATCHES                      0x01000000L
#define DDSCAPS2_NPATCHES                       0x02000000L
#define DDSCAPS2_RESERVED3                      0x04000000L
#define DDSCAPS2_DISCARDBACKBUFFER              0x10000000L
#define DDSCAPS2_ENABLEALPHACHANNEL             0x20000000L
#define DDSCAPS2_EXTENDEDFORMATPRIMARY          0x40000000L
#define DDSCAPS2_ADDITIONALPRIMARY              0x80000000L

#define DDSCAPS3_MULTISAMPLE_MASK               0x0000001FL
#define DDSCAPS3_MULTISAMPLE_QUALITY_MASK       0x000000E0L
#define DDSCAPS3_MULTISAMPLE_QUALITY_SHIFT      5
#define DDSCAPS3_RESERVED1                      0x00000100L
#define DDSCAPS3_RESERVED2                      0x00000200L
#define DDSCAPS3_LIGHTWEIGHTMIPMAP              0x00000400L
#define DDSCAPS3_AUTOGENMIPMAP                  0x00000800L
#define DDSCAPS3_DMAP                           0x00001000L

#define DDPF_ALPHAPIXELS                        0x00000001l
#define DDPF_ALPHA                              0x00000002l
#define DDPF_FOURCC                             0x00000004l
#define DDPF_PALETTEINDEXED4                    0x00000008l
#define DDPF_PALETTEINDEXEDTO8                  0x00000010l
#define DDPF_PALETTEINDEXED8                    0x00000020l
#define DDPF_RGB                                0x00000040l
#define DDPF_COMPRESSED                         0x00000080l
#define DDPF_RGBTOYUV                           0x00000100l
#define DDPF_YUV                                0x00000200l
#define DDPF_ZBUFFER                            0x00000400l
#define DDPF_PALETTEINDEXED1                    0x00000800l
#define DDPF_PALETTEINDEXED2                    0x00001000l
#define DDPF_ZPIXELS                            0x00002000l
#define DDPF_STENCILBUFFER                      0x00004000l
#define DDPF_ALPHAPREMULT                       0x00008000l
#define DDPF_LUMINANCE                          0x00020000l
#define DDPF_BUMPLUMINANCE                      0x00040000l
#define DDPF_BUMPDUDV                           0x00080000l


typedef struct  {
    DWORD dwCaps1;
    DWORD dwCaps2;
    DWORD Reserved[2];
} DDSCAPS2;

typedef struct  {
    DWORD dwSize;
    DWORD dwFlags;
    DWORD dwFourCC;
    DWORD dwRGBBitCount;
    DWORD dwRBitMask;
	DWORD dwGBitMask;
	DWORD dwBBitMask;
    DWORD dwRGBAlphaBitMask;
} DDPIXELFORMAT;

typedef struct  {
	DWORD dwSize;
	DWORD dwFlags;
	DWORD dwHeight;
	DWORD dwWidth;
	DWORD dwPitchOrLinearSize;
	DWORD dwDepth;
	DWORD dwMipMapCount;
	DWORD dwReserved1[11];
	DDPIXELFORMAT ddpfPixelFormat;
	DDSCAPS2 ddsCaps;
	DWORD dwReserved2;
} DDSURFACEDESC2;

typedef struct  {
	BYTE A;
	BYTE R;
	BYTE G;
	BYTE B;
} COLOR;

void default_header(DWORD* dwMagic, DDSURFACEDESC2* header)
{
	*dwMagic = 0x20534444;
	ZeroMemory(header, sizeof(DDSURFACEDESC2));
	header->dwSize = sizeof(DDSURFACEDESC2);
	header->dwFlags = DDSD_CAPS | DDSD_HEIGHT | DDSD_WIDTH | DDSD_PIXELFORMAT | DDSD_DEPTH | DDSD_PITCH;
	header->dwHeight = 128;
	header->dwWidth = 128;
	header->dwDepth = 128;
	header->dwPitchOrLinearSize = sizeof(DWORD) * header->dwWidth * header->dwDepth;
	header->ddpfPixelFormat.dwSize = sizeof(DDPIXELFORMAT);
	header->ddpfPixelFormat.dwFlags = DDPF_RGB | DDPF_ALPHAPIXELS;
	header->ddpfPixelFormat.dwRGBBitCount = 32;
	header->ddpfPixelFormat.dwRBitMask = 0x00ff0000;
	header->ddpfPixelFormat.dwGBitMask = 0x0000ff00;
	header->ddpfPixelFormat.dwBBitMask = 0x000000ff;
	header->ddpfPixelFormat.dwRGBAlphaBitMask = 0xff000000;
	header->ddsCaps.dwCaps1 = DDSCAPS_TEXTURE | DDSCAPS_COMPLEX;
	header->ddsCaps.dwCaps2 = DDSCAPS2_VOLUME;
}

void write(const char* path, DWORD* data){
	FILE* file;
	DWORD dwMagic;
	DDSURFACEDESC2 header;
	if((file = fopen(path, "wb")) == NULL){
		printf("Failed write %s\n", path);
		return;
	}
	default_header(&dwMagic, &header);
	fwrite(&dwMagic, sizeof(DWORD), 1, file);
	fwrite(&header, sizeof(DDSURFACEDESC2), 1, file);
	fwrite(data, sizeof(DWORD), header.dwHeight * header.dwWidth * header.dwDepth, file);
	fclose(file);
}

double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
double lerp(double t, double a, double b) { return a + t * (b - a); }
double grad(int hash, double x, double y, double z) {
      int h = hash & 15;                      // CONVERT LO 4 BITS OF HASH CODE
      double u = h<8 ? x : y,                 // INTO 12 GRADIENT DIRECTIONS.
             v = h<4 ? y : h==12||h==14 ? x : z;
      return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
   }
const int permutation[] = { 151,160,137,91,90,15,
   131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
   190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
   88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
   77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
   102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
   135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
   5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
   223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
   129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
   251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
   49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
   138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
   };
_inline int p(int a){
	return permutation[a % 256];
}
double noise(double x, double y, double z) {
	  double u, v, w;
      int A, AA, AB, B, BA, BB;
      int X = (int)(x) & 255,                  // FIND UNIT CUBE THAT
          Y = (int)(y) & 255,                  // CONTAINS POINT.
          Z = (int)(z) & 255;
      x -= (int)(x);                                // FIND RELATIVE X,Y,Z
      y -= (int)(y);                                // OF POINT IN CUBE.
      z -= (int)(z);
      u = fade(x),                                // COMPUTE FADE CURVES
      v = fade(y),                                // FOR EACH OF X,Y,Z.
      w = fade(z);
      A = p(X  )+Y, AA = p(A)+Z, AB = p(A+1)+Z,      // HASH COORDINATES OF
      B = p(X+1)+Y, BA = p(B)+Z, BB = p(B+1)+Z;      // THE 8 CUBE CORNERS,

      return lerp(w, lerp(v, lerp(u, grad(p(AA  ), x  , y  , z   ),  // AND ADD
                                     grad(p(BA  ), x-1, y  , z   )), // BLENDED
                             lerp(u, grad(p(AB  ), x  , y-1, z   ),  // RESULTS
                                     grad(p(BB  ), x-1, y-1, z   ))),// FROM  8
                     lerp(v, lerp(u, grad(p(AA+1), x  , y  , z-1 ),  // CORNERS
                                     grad(p(BA+1), x-1, y  , z-1 )), // OF CUBE
                             lerp(u, grad(p(AB+1), x  , y-1, z-1 ),
                                     grad(p(BB+1), x-1, y-1, z-1 ))));
}

double pnoise(double x, double y, double z) {
	double f = 1.0, a = 0.5;
	double total = 0.0;
	int i;
	for(i = 0; i < 15; ++i){
		total += noise(x * f, y * f, z * f) * a;
		f *= 2.0;
		a *= 0.5;
	}
	return total;
}

void perlin_noise(COLOR* data){
	int i, j, k;
	for(i = 0; i < 128; ++i){
		for(j = 0; j < 128; ++j){
			for(k = 0; k < 128; ++k){
				int index = i * 128 * 128 + j * 128 + k;
				double x, y, z, m, n, o, p;
				BYTE b, c, d, e;
				x = k * 0.05;
				y = j * 0.05;
				z = i * 0.1;
				m = pnoise(x, y, z);
				n = pnoise(x, y, z + 12.8);
				o = pnoise(x, y, z + 25.6);
				p = pnoise(x, y, z + 38.4);
				b = (BYTE)(255 * (m * 0.5 + 0.5));
				c = (BYTE)(255 * (n * 0.5 + 0.5));
				d = (BYTE)(255 * (o * 0.5 + 0.5));
				e = (BYTE)(255 * (p * 0.5 + 0.5));
				data[index].R = b;
				data[index].G = c;
				data[index].B = d;
				data[index].A = e;
				//printf("%lf %lf %lf %lf\n", x, y, z, n);
				//getchar();
			}
		}
	}
}

int main()
{
	char path[512];
	COLOR* data;
	printf("output file > ");
	scanf("%s", path);
	data = (COLOR*)malloc(sizeof(COLOR) * 128 * 128 * 128);
	perlin_noise(data);
	write(path, (DWORD*)data);
	free(data);
	return 0;
}