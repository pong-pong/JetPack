## JetPack
클래스와 클래스 필드에 선언된 어노테이션을 통해 자동적으로 객체 정보를 읽어들여 자동으로 JSON 형식의 String 으로 변환해주는 라이브러리입니다. 

-----

### How to use
정보를 읽어올 객체의 클래스에 아래와 같이 어노테이션을 선언합니다.

- `@JsonObject` : 클래스 전체를 데이터화 시킬 때 클래스에 선언합니다
- `@JsonKey` : 필드를 데이터화 시킬 때 해당 필드에 선언합니다.
- `FormatType` : `JsonFormatter.toJSON()` 을 통해 데이터를 포매팅 하게되면 기준 포맷을 설정할 수 있는데, 이를 따로 지정해주지 않았을 때 클래스 레벨과 필드 레벨 모두 선언된 클래스일 경우 우선순위를 자동으로 지정해줍니다. 이는 `toJSON()` 메소드와 함께 아래에서 설명합니다.

```Java
import jetpack.FormatType;
import jetpack.annotation.JsonKey;
import jetpack.annotation.JsonObject;

@JsonObject(formatBy = FormatType.OBJECT)
public class Student {

    @JsonKey
    private String name;

    @JsonKey
    private String school;

    @JsonKey
    private String major;

    private int age;

    private Student[] friends;

    public Student(String name, String school, String major, int age, Student ... friends) {
        this.name = name;
        this.school = school;
        this.major = major;
        this.age = age;
        this.friends = friends;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student[] getFriends() {
        return friends;
    }

    public void setFriends(Student[] friends) {
        this.friends = friends;
    }
}

```

위와 같이 선언한 클래스의 객체를 아래와 같이 `JsonFormatter` 클래스의 `toJSON()` 메소드를 통해 포매팅할 수 있습니다.

```Java
public void main(String[] args){
        Student s = new Student("Bella", "Daedeok SW Meister HighSchool", "SW", 19);
        
        try {
            JsonFormatter.toJSON(s)
        } catch (AnnotationNotFoundException | MissingKeyNameException e) {
            e.printStackTrace();
        }
}
```
포매팅 결과
```
{name: "Bella", school: "Daedeok SW Meister HighSchool", major: "SW", age: 19, friends: []}
```

`@JsonObject` 의 디폴트 `FormatType` 을 `FormatType.OBJECT` 로 지정해주었기 때문에 `@JsonKey` 어노테이션을 붙이지 않은 `age` 와 `friends` 필드도 함께 포맷됩니다. 만약 `@JsonKey` 를 선언한 특정 필드들만 포매팅시키고 싶다면 `toJSON()` 메소드에 타입을 지정해주면 됩니다.

-----

### `toJSON()` 메소드 
``` Java
public static toJSON(String k, Object o, FormatType t, boolean n) throws AnnotationNotFoundException, MissingKeyNameException {
  /...
}
```

- k : Array 형태의 객체를 넣을 경우 Key 값 (아닐경우 생략 가능)
- o : 포맷할 대상 객체 (필수)
- t : 포맷 타입 (생략 가능, 생략 시 @JsonObject 어노테이션의 디폴트 FormatType 사용)
- n : null 값 허용 여부 (default false)

-----

### 메소드 사용 예제

사용할 객체

```Java
Student s = new Student("Bella", null, "SW", 19);
```

#### (Object o)

``` Java
JsonFormatter.toJSON(s);
```

출력결과

```Java
{name: "Bella", major: "SW", age: 19, friends: []}
```

#### (Object o, FormatType t)

``` Java
JsonFormatter.toJSON(s, FormatType.KEY);
```

출력결과

```Java
{name: "Bella", major: "SW"}
```

#### (Object o, FormatType t, boolean n)

``` Java
JsonFormatter.toJSON(s, FormatType.Object, true);
```

출력결과
 
```Java
{name: "Bella", school: null, major: "SW", age: 19, friends: []}
```

#### (String k, Object o, FormatType t, boolean n)

``` Java
Student[] students = {...};
JsonFormatter.toJSON("students", students, FormatType.key, false);
```

출력결과

```Java
{students: [/* student 객체들 */]}
```

-----
### 지원 범위
#### 현재 지원 범위
- 커스텀 클래스
- 원시타입
- 참조타입 (String)

#### 지원 예정 범위
- 컬렉션
