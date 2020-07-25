
import Vue from 'vue'
import {
  Button,
  Form,
  FormItem,
  Input,
  InputNumber,
  Checkbox,
  Select,
  DatePicker,
  Tree,
  Badge,
  Message,
  Container,
  Header,
  Main,
  Aside,
  Menu,
  MenuItem,
  Submenu,
  MenuItemGroup,
  Breadcrumb,
  BreadcrumbItem,
  Card,
  Row,
  Col,
  Table,
  TableColumn,
  Switch,
  Tooltip,
  Pagination,
  Tabs,
  TabPane,
  Loading,
  MessageBox,
  ButtonGroup,
  Dialog,
  Drawer,
  Upload,
  Option,
  Popover
} from 'element-ui'

Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(InputNumber)
Vue.use(Checkbox)
Vue.use(Select)
Vue.use(DatePicker)
// Vue.use(DateTimePicker)
Vue.use(Tree)
Vue.use(Badge)
Vue.use(Container)
Vue.use(Header)
Vue.use(Main)
Vue.use(Aside)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(Submenu)
Vue.use(MenuItemGroup)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Card)
Vue.use(Row)
Vue.use(Col)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Switch)
Vue.use(Tooltip)
Vue.use(Pagination)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(ButtonGroup)
Vue.use(Dialog)
Vue.use(Drawer)
Vue.use(Loading)
Vue.use(Loading.directive)
Vue.use(Upload)
Vue.use(Option)
Vue.use(Popover)
Vue.prototype.$loading = Loading.service
Vue.prototype.$msgbox = MessageBox
Vue.prototype.$alert = MessageBox.alert
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$prompt = MessageBox.prompt
Vue.prototype.$notify = Notification
Vue.prototype.$message = Message

Vue.prototype.$ELEMENT = { size: 'small' }
