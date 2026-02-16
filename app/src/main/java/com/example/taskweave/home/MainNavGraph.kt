package com.example.taskweave.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.taskweave.home.presentation.projectScreen.CreateProjectScreen
import com.example.taskweave.home.presentation.projectScreen.JoinProjectScreen
import com.example.taskweave.home.presentation.projectScreen.ProjectScreen
import com.example.taskweave.home.presentation.projectScreen.ProjectViewModel
import com.example.taskweave.home.presentation.taskScreen.TaskScreen
import com.example.taskweave.home.presentation.taskScreen.TaskViewModel

object HomeRoutes {
    const val PROJECT_LIST = "project_list"
    const val CREATE_PROJECT = "create_project"
    const val JOIN_PROJECT = "join_project"
    const val TASK = "task"
    const val TASK_WITH_ARG = "task/{projectId}"
}


@Composable
fun HomeNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController, startDestination = HomeRoutes.PROJECT_LIST,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(100)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(100)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(100)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(100)) }
    ) {
        composable(HomeRoutes.PROJECT_LIST) {
            val viewModel: ProjectViewModel = hiltViewModel()
            ProjectScreen(
                projectViewModel = viewModel,
                onNavigateToTasks = { projectId ->
                    navController.navigate("${HomeRoutes.TASK}/$projectId") {
                        launchSingleTop = true
                    }
                },
                onNavigateToCreateProject = {
                    navController.navigate(HomeRoutes.CREATE_PROJECT)
                },
                onNavigateToJoinProject = {
                    navController.navigate(HomeRoutes.JOIN_PROJECT)
                },
            )
        }

        composable(HomeRoutes.CREATE_PROJECT) {
            val viewModel: ProjectViewModel = hiltViewModel()
            CreateProjectScreen(onCreateProject = { name ->
                viewModel.createProject(name)
                navController.popBackStack()
            }, onBack = { navController.popBackStack() })
        }

        composable(HomeRoutes.JOIN_PROJECT) {
            val viewModel: ProjectViewModel = hiltViewModel()

            JoinProjectScreen(
                onJoinProject = { projectId -> viewModel.joinProject(projectId) },
                onBack = { navController.popBackStack() })
        }

        composable(
            HomeRoutes.TASK_WITH_ARG, arguments = listOf(
                navArgument("projectId") { type = NavType.StringType })
        ) {
            val viewModel: TaskViewModel = hiltViewModel()

            TaskScreen(
                viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}