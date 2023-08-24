#define bool _Bool

int printf(const char *pattern, ...);
int scanf(const char *restrict, ...);
int sprintf(char *, const char *, ...);
int sscanf(const char *restrict, const char *restrict, ...);
char *strcpy(char *, const char *);
char *strcat(char *, const char *);
int strcmp(const char *, const char *);
unsigned int strlen(const char *);
void *memcpy(char *,const char*,unsigned int);
void* malloc(unsigned int);

struct _array{
    int size;
    char* ptr;
};
int _arraySize(struct _array* a){
    return a->size;
}
char* _arrayElement(struct _array* a,int in){
    return &((a->ptr)[in]);
}
void print(char *s) {printf("%s", s);}

void println(char *s) {printf("%s\n", s);}

void printInt(int n) {printf("%d", n);}

void printlnInt(int n) {printf("%d\n", n);}

char *_malloc(int n) {return malloc(n);}


char *getString()
{
	char *ans = malloc(1024);
	scanf("%s", ans);
	return ans;
}

int getInt()
{
	int ans;
	scanf("%d", &ans);
	return ans;
}

char *toString(int i)
{
	char *ans = malloc(16);
	sprintf(ans, "%d", i);
	return ans;
}


char *_string_plus(char *lhs, char *rhs)
{
	char *ans = malloc(1024);
	strcpy(ans, lhs);
	strcat(ans, rhs);
	return ans;
}

bool _string_eq(char *lhs, char *rhs) {return strcmp(lhs, rhs) == 0;}

bool _string_ne(char *lhs, char *rhs) {return strcmp(lhs, rhs) != 0;}

bool _string_l(char *lhs, char *rhs) {return strcmp(lhs, rhs) < 0;}

bool _string_g(char *lhs, char *rhs) {return strcmp(lhs, rhs) > 0;}

bool _string_le(char *lhs, char *rhs) {return strcmp(lhs, rhs) <= 0;}

bool _string_ge(char *lhs, char *rhs) {return strcmp(lhs, rhs) >= 0;}

int _string_length(char *s) {return strlen(s);}

char *_string_substring(char *s, int l, int r)
{
	char *ans = malloc( r - l + 1);
	memcpy(ans, s + l, r - l);
	ans[r - l] = '\0';
	return ans;
}

int _string_parseInt(char *s)
{
	int ans;
	sscanf(s, "%d", &ans);
	return ans;
}

int _string_ord(char *s, int pos) {return s[pos];}