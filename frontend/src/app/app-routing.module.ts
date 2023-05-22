import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EventsComponent} from './components/events/events.component';
import {RegisterComponent} from './components/register/register.component';
import {NewsCreateComponent} from './components/news/news-create/news-create.component';
import {NewsComponent} from './components/news/news.component';
import {AdminRouteGuard} from './guards/admin-route.guard';
import { RoomplaneditorComponent } from './components/roomplaneditor/roomplaneditor.component';
import {EventOverviewComponent} from './components/event-overview/event-overview.component';


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'events', canActivate: [AdminRouteGuard], component: EventsComponent},
  {path: 'events-overview', canActivate: [AuthGuard], component: EventOverviewComponent},
  {path: 'news', canActivate: [AuthGuard], component: NewsComponent},
  {path: 'news/create', canActivate: [AdminRouteGuard], component: NewsCreateComponent},
  {path: 'roomplan/:id/edit', component: RoomplaneditorComponent }
];


@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
