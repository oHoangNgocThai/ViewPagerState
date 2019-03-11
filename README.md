# ViewPager Save State

# Giới thiệu
> Chắc hẳn các bạn lập trình không còn xa lạ gì với việc làm việc với ViewPager. Bài viết sau đây sẽ giải thích một cách cặn kẽ về việc ViewPager nó lưu trữ các trang như thế nào và cách để lưu giữ trạng thái của những Fragment bên trong đó.

Điểm qua về ViewPager, nó được coi là trình quản lý bố cục cho phép người dùng có thể thao tác lật trái và lật phải tương ứng trên màn hình để có thể chuyển qua lại dữ liệu.
Ví dụ như hình dưới đây

![](https://www.journaldev.com/wp-content/uploads/2016/10/android-tab-layout-view-pager-issue.gif)

# ViewPager
## Cấu trúc

* ViewPager thường được sử dụng với Fragment vì ViewPager quản lý vòng đời của các Fragment có trong nó một cách thuận tiện nhất.
Mặc định, ViewPager sẽ nạp 3 Fragment vào trong bộ nhớ của nó để có thể có trải nghiệm tốt nhất cho người dùng và tiết kiệm bộ nhớ của hệ thống.

> Ví dụ: Khi bạn có 3 Fragment ở bên trong ViewPager thì khi bạn mở Fragment thứ nhất, Hệ thống sẽ nạp và khởi tạo Fragment thứ 2 trong bộ nhớ của nó. Tương tự vậy, khi bạn chuyển qua Fragment thứ 2 thì nó sẽ nạp và khởi tạo Fragment thứ 3 sẵn cho bạn. Lúc này thì cơ chế nạp 3 Fragment của ViewPager được thể hiện rõ nhất. Và khi bạn chuyển qua Fragment 3 thì sẽ không có Fragment nào được nạp phía bên phải của nó, mà thay vào đó là hủy đi Fragment thứ nhất. Chính vì việc đó nên ViewPager sẽ tiết kiệm được bộ nhớ nếu mà số lượng Fragment rất nhiều (giả sử như ứng dụng đọc sách chẳng hạn, số lượng nội dung sẽ là rất nhiều).  Mặc định ViewPager chỉ giữ trạng thái của một Fragment khi mà nó không còn xuất hiện nữa trong thứ tự các trang, điều này sẽ được chỉnh bởi thuộc tính **offScreenPagerLimit(Int)** mặc định có giá trị là 1. Nếu bạn muốn giữ nhiều Fragment hơn là 1 thì bạn sẽ cần thay đổi tham số truyền vào của hàm này.

* Chính vì cơ chế nạp này của ViewPager mà có trường hợp xảy ra như sau: Bạn đang điền dữ liệu dở ở Fragment thứ nhất và bạn chuyển qua lại giữa các Fragment sau đó và quay trở lại, dữ liệu của Fragment lúc này đã bị mất khi bạn đang nhập và bạn phải thao tác lại từ đầu. Đây chính là bài toán cần giải quyết trong bài viết này. Lúc này ta sẽ muốn làm thế nào để lưu được trạng thái của Fragment và sau khi quay lại thì có thể khôi phục được thao tác của người dùng.

## Adapter
Đầu tiên chúng ta cần hiểu ViewPager cung cấp cho chúng ta các Adapter để dễ dàng trong việc quản lý các Fragment. Có 2 lớp là **FragmentPagerAdapter** và **FragmentStatePagerAdapter** là 2 Adapter thường được sử dụng để quản lý các Fragment của ViewPager. Việc sinh ra 2 lớp này sinh ra để cùng quản lý các Fragment, nhưng mỗi cái lại quản lý 1 cách khác nhau:

* FragmentPagerAdapter: Adapter này thích hợp với ViewPager có số lượng ít các Fragment, vì trong trường hợp này các Fragment không còn hiển thị sẽ được lưu trữ lại trong bộ nhớ của hệ thống và chỉ hủy đi các View. Lúc quay trở lại Fragment cũ thì nó chỉ khởi tạo lại View cho Fragment đó trong hàm onCreateView() chứ không khởi tạo lại Fragment đó.
* FragmentStatePagerAdapter: Adapter này thích hợp với ViewPager có số lượng nhiều các Fragment, vì trong trường hợp này các Fragment không dùng đến sẽ bị hủy khỏi hệ thống và chỉ lưu trữ lại trạng thái của nó. Khi quay trở lại nó sẽ khởi tạo lại cả Fragment đó, việc cần làm trong này chính là khôi phục lại trạng thái của Fragment bằng hàm **onSaveInstanceState()**.

Nói qua một chút về lý thuyết để thấy trường hợp sử dụng FragmentStatePagerAdapter bạn cần chú ý đến việc khôi phục lại trạng thái đã có của Fragment thay vì phải lưu hết tất cả các Fragment vào hệ thống. Lan man lý thuyết hơi nhiều, sau đây sẽ có ví dụ cụ thể về trường hợp phải lưu trạng thái khi sử dụng ViewPager.

# Thực hiện

**Ví dụ dưới đây là việc kết hợp giữa TabLayout và ViewPager để có thể tạo ra ViewPager có nhiều Fragment trong đó.**

Mình tạo ra 3 tab lần lượt là HomeFragment, ContactFragment và SettingsFragment. Ở đây mình ví dụ với Fragment Home
```
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

}
```

-----
Sử dụng hàm newInstance để có thể khởi tạo được instance khác nhau của ViewPager
```
  public static HomeFragment newInstance(int page, String title) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        homeFragment.setArguments(args);
        return homeFragment;
    }

```
Trong Fragment Home tôi có 1 EditText, việc cần làm là khi tôi nhập vào EditText và chuyển đi Fragment khác, sau khi quay lại EditText của tôi vẫn sẽ có dữ liệu mà tôi đang nhập ở đó. Việc xử lý lưu trạng thái được thực hiện trong hàm onSaveInstanceState() của Fragment như sau:
```
 @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String text = mEditText.getText().toString().trim();
        if (text != null) {
            outState.putString("text", text);
        }
    }
```
Trạng thái này sẽ được lưu trữ dưới dạng Bundle và khi quay về Fragment này, hệ thống sẽ trả về Bundle có chứa dữ liệu trạng thái mà bạn đã lưu để có thể lấy ra và cập nhật lại trạng thái. Trong Fragment bạn có thể override lại hàm onViewStateRestored() để có thể lấy ra đối tượng Bundle lưu giữ trạng thái và set vào EditText trạng thái cũ của nó.
```
@Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            String text_old = savedInstanceState.getString("text");
            mEditText.setText(text_old);
        }
    }
```
