#define bool _Bool
#include "stdlib.h"
#include "string.h"
#include "stdio.h"

struct _array{
    int size;
    char* ptr;
};
int _arraySize(struct _array* a){
    return a->size;
}
char* _arrayElement(struct _array* a,int in){
    return (a->ptr)+in;
}
void print(const char *s) {printf("%s", s);}

void println(const char *s) {printf("%s\n", s);}

void printInt(int n) {printf("%d", n);}

void printlnInt(int n) {printf("%d\n", n);}

char *_malloc(int n) {return malloc(n);}


const char *getString()
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


char *string_plus(char *lhs, char *rhs)
{
	char *ans = malloc(1024);
	strcpy(ans, lhs);
	strcat(ans, rhs);
	return ans;
}

bool string_eq(char *lhs, char *rhs) {return strcmp(lhs, rhs) == 0;}

bool string_ne(char *lhs, char *rhs) {return strcmp(lhs, rhs) != 0;}

bool string_l(char *lhs, char *rhs) {return strcmp(lhs, rhs) < 0;}

bool string_g(char *lhs, char *rhs) {return strcmp(lhs, rhs) > 0;}

bool string_le(char *lhs, char *rhs) {return strcmp(lhs, rhs) <= 0;}

bool string_ge(char *lhs, char *rhs) {return strcmp(lhs, rhs) >= 0;}

int string_length(char *s) {return strlen(s);}

char *string_substring(char *s, int l, int r)
{
	char *ans = malloc( r - l + 1);
	memcpy(ans, s + l, r - l);
	ans[r - l] = '\0';
	return ans;
}

int string_parseInt(char *s)
{
	int ans;
	sscanf(s, "%d", &ans);
	return ans;
}

int string_ord(char *s, int pos) {return s[pos];}